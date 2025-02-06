package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostUpdateDto;
import com.oplusz.festgo.dto.PostWithAttachmentsDto;
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

	/**
	 * 게시글 목록 조회
	 */
	@GetMapping("/list")
	public String list(Model model) {
		log.debug("list()");
		List<Post> list = postService.read();
		model.addAttribute("posts", list);
		return "post/list";
	}

	/**
	 * 게시글 상세 조회 (조회수 증가)
	 */
	@GetMapping("/details")
	public String details(@RequestParam("poId") Integer poId, Model model) {
	    // 게시글 + 첨부파일 조회
	    PostWithAttachmentsDto postDto = postService.readById(poId);
	    model.addAttribute("postWithAttachments", postDto); // 여기서 제대로 전달되고 있는지 확인
	    model.addAttribute("imageAttachments", postDto.getAttachments()); // 첨부파일
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
	public String showCreatePage() {
		log.debug("GET create page");
		return "post/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute PostCreateDto dto,
			@RequestParam(value = "files", required = false) List<MultipartFile> files, HttpSession session) {
		log.debug("POST create(dto={}, files={})", dto, files);

		// 세션에서 사용자 역할(mrId) 가져오기
		Integer mrId = (Integer) session.getAttribute("mrId");
		if (mrId == null) {
			mrId = 1; // 기본값: 일반 사용자
		}

		// pcId 값 설정 (관리자만 1 또는 2 선택 가능)
		if (mrId != 3) {
			dto.setPcId(1);
		} else {
			if (dto.getPcId() == null || (dto.getPcId() != 1 && dto.getPcId() != 2)) {
				dto.setPcId(1);
			}
		}

		log.debug("최종 pcId 값: {}", dto.getPcId());

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
	public String delete(@RequestParam(name = "poId") Integer poId) {

		log.debug("delete(id={})", poId);
		if (poId == null) {
			log.error("삭제할 게시글 ID가 없습니다.");
			return "redirect:/post/list"; // 잘못된 요청 시 목록으로 이동
		}

		postService.delete(poId);

		return "redirect:/post/list";
	}
	@GetMapping("/search")
	public String search(PostSearchDto dto, Model model) {
		log.debug("search(dto={})",dto);
		
		// 서비스 계층의 메서드를 호출해서 검색 결과 리스트를 가져옴
		List<Post> list = postService.read(dto);
		// 검색 결과를 뷰에게 전달
		model.addAttribute("posts",list);
		
		return"post/list"; 
	}

}
