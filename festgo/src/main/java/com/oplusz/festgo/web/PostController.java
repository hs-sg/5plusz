package com.oplusz.festgo.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostSearchDto;
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

	/**
	 * 게시글 목록 조회 (페이징 포함)
	 */
	@GetMapping("/list")
	public String getPagedPosts(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int pageSize, Model model) {

		log.debug("Fetching paged posts - page: {}, pageSize: {}", page, pageSize);

		Map<String, Object> result = postService.getPagedPosts(page, pageSize);
		model.addAttribute("posts", result.get("posts"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		model.addAttribute("pageSize", result.get("pageSize"));

		return "post/list";
	}

	/**
	 * 게시글 상세 조회 (조회수 증가)
	 */
	@GetMapping("/details")
	public String details(@RequestParam("poId") Integer poId, Model model) {
		// 게시글 + 첨부파일 조회
		PostWithAttachmentsDto postDto = postService.readById(poId);

		// JSP에서 사용할 수 있도록 Model에 추가
		model.addAttribute("postWithAttachments", postDto);
		return "post/details";
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

	/**
	 * 검색 및 페이징 처리된 게시글 목록 조회
	 */
	@GetMapping("/search")
	public String search(@RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
	        Model model) {
	    if (page < 1) {
	        page = 1;
	    }

	    log.debug("Parameter values - category: '{}', keyword: '{}', page: {}, pageSize: {}",
	            category, keyword, page, pageSize);

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
	    model.addAttribute("posts", result.get("posts"));
	    model.addAttribute("currentPage", result.get("currentPage"));
	    model.addAttribute("totalPages", result.get("totalPages"));
	    model.addAttribute("pageSize", result.get("pageSize"));
	    model.addAttribute("category", category);
	    model.addAttribute("keyword", keyword);

	    return "post/list";
	}

}
