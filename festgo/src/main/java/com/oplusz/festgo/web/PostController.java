package com.oplusz.festgo.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostSearchDto;
import com.oplusz.festgo.dto.PostUpdateDto;
import com.oplusz.festgo.dto.PostWithAttachmentsDto;
import com.oplusz.festgo.service.MyPageService;
import com.oplusz.festgo.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private static final String UPLOAD_DIR = "C:/JAVA157/Workspaces/oplusz/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/festgo/uploads/";
	private final MyPageService myPageService;

	/**
	 * 게시글 목록 조회 (페이징 포함)
	 */
	// 게시글 목록 조회 (공지사항 먼저 가져오기)
	@GetMapping("/list")
	public String getPagedPosts(@RequestParam(defaultValue = "1") int page,
	                            @RequestParam(required = false) Integer pageSize, 
	                            Model model) {

	    if (pageSize == null || pageSize <= 0) {
	        pageSize = 5; // 기본값 설정
	    }

	    // 공지사항은 1페이지에서만 가져오도록 처리
	    List<Post> notices = (page == 1) ? postService.getNotices() : Collections.emptyList();

	    // 일반 게시글 페이징 처리
	    Map<String, Object> result = postService.getPagedPosts(page, pageSize);

	    // 모델에 데이터 추가
	    model.addAttribute("notices", notices); // 공지사항 (1페이지에서만 추가)
	    model.addAttribute("posts", result.get("posts")); // 일반 게시글
	    model.addAttribute("currentPage", result.get("currentPage"));
	    model.addAttribute("pageSize", result.get("pageSize"));
	    model.addAttribute("totalPages", result.get("totalPages"));

	    return "post/list"; 
	}


	/**
	 * 게시글 상세 조회 (조회수 증가)
	 */
	@GetMapping("/details")
	public String details(@RequestParam("poId") Integer poId, Model model, HttpSession session) {
	    // 조회수 중복 증가 방지
	    String viewedPostKey = "viewedPost_" + poId;
	    if (session.getAttribute(viewedPostKey) == null) {
	        postService.increaseViewCount(poId);
	        session.setAttribute(viewedPostKey, true);
	    }

	    PostWithAttachmentsDto postDto = postService.readById(poId);
	    model.addAttribute("postWithAttachments", postDto);
	    model.addAttribute("imageAttachments", postDto.getAttachments());

	    return "post/details";
	}



	// 이미지 미리보기 메서드
	@GetMapping("/uploads/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
	    try {
	        // 파일 시스템에서 리소스 생성
	        Resource resource = new FileSystemResource(UPLOAD_DIR + fileName);
	        
	        if (resource.exists()) {
	            return ResponseEntity.ok()
	                    .contentType(MediaType.IMAGE_JPEG) // 또는 적절한 미디어 타입
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().build();
	    }
	}

	/*
	 * * 게시글 수정 화면 (조회수 증가 X)
	 */
	@GetMapping("/modify")
	public String modify(@RequestParam("poId") Integer poId, Model model) {
		// 수정 페이지에서는 조회수 증가 없이 가져오기
		PostWithAttachmentsDto postDto = postService.getPostWithoutIncreasingViews(poId);

		// JSP에서 사용할 수 있도록 Model에 추가
		model.addAttribute("postWithAttachments", postDto);
		return "post/modify";
	}

	@GetMapping("/create")
	public String showCreatePage(HttpSession session, Model model) {
	    // 로그인된 사용자 확인
	    String signedInUser = (String) session.getAttribute("signedInUser");

	    if (signedInUser == null) {
	        return "redirect:/post/list?loginRequired=true"; // 로그인 요구 파라미터 추가
	    }

	    // 사용자 역할 가져오기 (DB 조회)
	    Integer mrId = myPageService.readRoleIdByUsername(signedInUser);
	    model.addAttribute("mrId", mrId);

	    log.debug("로그인된 사용자: {}, 역할 ID: {}", signedInUser, mrId);

	    return "post/create"; // 글쓰기 페이지로 이동
	}



	@PostMapping("/create")
	public String create(@ModelAttribute PostCreateDto dto,
	                     @RequestParam(value = "files", required = false) List<MultipartFile> files,
	                     HttpSession session) {
	    log.debug("POST create(dto={}, files={})", dto, files);

	    // 로그인된 사용자 확인
	    String signedInUser = (String) session.getAttribute("signedInUser");
	    if (signedInUser == null) {
	        return "redirect:/user/signin";
	    }

	    // 사용자의 mrId 조회
	    Integer mrId = myPageService.readRoleIdByUsername(signedInUser);
	    log.debug("로그인된 사용자의 mrId: {}", mrId);

	    // 게시글 유형(pcId) 설정
	    if (dto.getPcId() == null) {
	        dto.setPcId((mrId == 3) ? 2 : 1); // 관리자(3) → 공지사항(2), 그 외 → 일반글(1)
	    }

	    log.debug("최종 pcId 값: {}", dto.getPcId());

	    // 게시글 저장
	    int result = postService.create(dto, files);
	    return (result > 0) ? "redirect:/post/list" : "error";
	}



	@PostMapping("/update")
	public String update(@ModelAttribute PostUpdateDto dto,
	        @RequestParam(value = "files", required = false) List<MultipartFile> files,
	        @RequestParam(value = "removeFiles", required = false) List<Integer> removeFileIds) {
	    log.debug("POST update(dto={}, files={}, removeFiles={})", dto, files, removeFileIds);
	    log.debug("removeFileIds: {}", removeFileIds); // 여기서 removeFileIds의 값을 확인

	    // 파일 정보 DTO에 저장
	    dto.setNewAttachments(files);
	    dto.setRemoveAttachmentIds(removeFileIds);

	    // 게시글 및 첨부파일 업데이트 수행
	    postService.updatePost(dto, files);

	    return "redirect:/post/details?poId=" + dto.getPoId(); // 수정 후 상세 페이지로 이동
	}

	@GetMapping("/delete")
	public String delete(@RequestParam(name = "poId") Integer poId, HttpSession session) {
	    String signedInUser = (String) session.getAttribute("signedInUser");

	    if (signedInUser == null) {
	        return "redirect:/post/list?error=notAuthorized";
	    }

	    Integer userRole = myPageService.readRoleIdByUsername(signedInUser);
	    Post post = postService.readById(poId).getPost();

	    // 관리자이거나 작성자 본인이어야 삭제 가능
	    if (userRole == 3 || post.getPoAuthor().equals(signedInUser)) {
	        postService.delete(poId);
	        return "redirect:/post/list";
	    }

	    return "redirect:/post/list?error=notAuthorized";
	}

	@PostMapping("/delete-multiple")
	@ResponseBody
	public Map<String, Object> deleteMultiple(@RequestBody Map<String, List<Integer>> payload, HttpSession session) {
	    System.out.println("🚀 [DELETE] delete-multiple 실행됨!");
	    System.out.println("📌 삭제할 게시글 ID 목록: " + payload.get("postIds"));
	    
	    List<Integer> postIds = payload.get("postIds");
	    Map<String, Object> response = new HashMap<>();

	    if (postIds == null || postIds.isEmpty()) {
	        response.put("success", false);
	        response.put("message", "삭제할 게시글을 선택하세요.");
	        return response;
	    }

	    try {
	        postService.deleteMultiple(postIds, session);
	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", e.getMessage());
	    }

	    return response;
	}


	/**
	 * 검색 및 페이징 처리된 게시글 목록 조회
	 */
	@GetMapping("/search")
	public String search(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Model model) {
		if (page < 1) {
			page = 1;
		}

		log.debug("Parameter values - category: '{}', keyword: '{}', page: {}, pageSize: {}", category, keyword, page,
				pageSize);

		// 검색 DTO 생성
		PostSearchDto dto = new PostSearchDto();
		dto.setCategory(category);
		dto.setKeyword(keyword);
		dto.setPage(page);
		dto.setPageSize(pageSize);

		log.debug("Created DTO: {}", dto); // DTO 생성 후 로그

		// 검색 및 페이징 결과 조회
		Map<String, Object> result = postService.searchWithPaging(dto);

		log.debug("Search result map: {}", result); // 결과 맵 로그

		// 모델에 결과 데이터 추가 전 값들 확인
		log.debug("Posts: {}", result.get("posts"));
		log.debug("Current Page: {}", result.get("currentPage"));
		log.debug("Total Pages: {}", result.get("totalPages"));
		log.debug("Page Size: {}", result.get("pageSize"));

		// 모델에 결과 데이터 추가
		model.addAttribute("notices", result.get("notices")); 
		model.addAttribute("posts", result.get("posts"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		model.addAttribute("pageSize", result.get("pageSize"));
		model.addAttribute("category", category);
		model.addAttribute("keyword", keyword);

		return "post/list";
	}
	

}