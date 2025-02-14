package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.dto.ReviewCreateDto;
import com.oplusz.festgo.dto.ReviewItemDto;
import com.oplusz.festgo.dto.ReviewUpdateDto;
import com.oplusz.festgo.service.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;
	
	 @GetMapping("/average/{feId}")
	    public ResponseEntity<Double> getAverageGrade(@PathVariable Integer feId) {
	        double avgGrade = reviewService.getAverageGrade(feId);
	        return ResponseEntity.ok(avgGrade);
	    }

	@GetMapping("/{reId}")
	public ResponseEntity<ReviewItemDto> getReviewById(@PathVariable("reId") Integer reId) {
		
		log.debug("getCommentById(id={})", reId);
		
		ReviewItemDto review = reviewService.readById(reId);
		
		return ResponseEntity.ok(review);
	}
	
	@GetMapping("/all/{feId}")
	public ResponseEntity<List<ReviewItemDto>> getAllReviewsByFestivalId(@PathVariable Integer feId) {
		log.debug("getAllCommentsByPostId(feId={})", feId);
		
		List<ReviewItemDto> list = reviewService.readByFestivalId(feId);
		
		return ResponseEntity.ok(list);
		
	}
	
	@PostMapping
	public ResponseEntity<Integer> registerReview(@RequestBody ReviewCreateDto dto, HttpSession session) {
		// @RequestBody:
		// 디스패쳐 서블릿이 Ajax 요청에서 패킷 몸통(body)에 포함된 JSON 문자열을 읽고
		// jackson-databind 라이브러리를 사용해서 자바 객체로 변환 후 
		// 컨트롤러 메서드의 아규먼트로 전달 해줌
		
		// 로그인한 사용자 확인
	    String signedInUser = (String) session.getAttribute("signedInUser");
	    log.debug("✅ 로그인 사용자: {}", signedInUser);
		
		log.debug("registerComment(dto={})", dto);
		
		 // reAuthor가 null이면 세션의 signedInUser 값으로 설정
	    if (signedInUser != null) {
	        dto.setReAuthor(signedInUser);
	    }

	    // reAuthor가 여전히 null이면 로그 출력
	    if (dto.getReAuthor() == null) {
	        log.error("reAuthor가 null입니다! dto={}", dto);
	        return ResponseEntity.badRequest().body(0);  // 오류 반환
	    }
		
		int result = reviewService.create(dto);
		
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{reId}")
	public ResponseEntity<Integer> deleteReview(@PathVariable("reId") Integer reId) {
		log.debug("deleteComment(id={})",reId);
		
		int result = reviewService.delete(reId);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping("/{reId}")
	public ResponseEntity<Integer> updateReview(@PathVariable("reId") Integer reId, @RequestBody ReviewUpdateDto dto) {
		log.debug("updateComment(id={}, dto={})", reId, dto);
		
		dto.setReId(reId);
		int result = reviewService.update(dto);
		
		return ResponseEntity.ok(result);
		
	}
	
//	희성 작성 시작
	
//	@GetMapping("/delete")
//	public String delete(@RequestParam(name = "reId") Integer reId, HttpServletRequest request) {
//
//		log.debug("delete(id={})", reId);
//		if (reId == null) {
//			return "redirect:/mypage"; // 잘못된 요청 시 마이페이지로 이동
//		}
//
//		reviewService.delete(reId);
//		String header = request.getHeader("Referer");
//
//		return "redirect:" + header;
//	}
	
	
//	희성 작성 끝
}
