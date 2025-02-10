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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/{id}")
	public ResponseEntity<ReviewItemDto> getReviewById(@PathVariable Integer reId) {
		
		log.debug("getCommentById(id={})", reId);
		
		ReviewItemDto review = reviewService.readById(reId);
		
		return ResponseEntity.ok(review);
	}
	
	@GetMapping("/all/{feId}")
	public ResponseEntity<List<ReviewItemDto>> getAllReviewsByFestivalId(@PathVariable Integer feId) {
		log.debug("getAllCommentsByPostId(postId={})", feId);
		
		List<ReviewItemDto> list = reviewService.readByFestivalId(feId);
		
		return ResponseEntity.ok(list);
		
	}
	
	@PostMapping
	public ResponseEntity<Integer> registerReview(@RequestBody ReviewCreateDto dto) {
		// @RequestBody:
		// 디스패쳐 서블릿이 Ajax 요청에서 패킷 몸통(body)에 포함된 JSON 문자열을 읽고
		// jackson-databind 라이브러리를 사용해서 자바 객체로 변환 후 
		// 컨트롤러 메서드의 아규먼트로 전달 해줌
		
		log.debug("registerComment(dto={})", dto);
		
		int result = reviewService.create(dto);
		
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteReview(@PathVariable Integer reId) {
		log.debug("deleteComment(id={})",reId);
		
		int result = reviewService.delete(reId);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Integer> updateReview(@PathVariable Integer reId, @RequestBody ReviewUpdateDto dto) {
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
