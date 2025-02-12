package com.oplusz.festgo.web;

import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oplusz.festgo.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;

	
	
//	희성 작성 시작
	
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "reId") Integer reId, HttpServletRequest request) {

		log.debug("delete(id={})", reId);
		if (reId == null) {
			return "redirect:/mypage"; // 잘못된 요청 시 마이페이지로 이동
		}

		reviewService.delete(reId);
		String header = request.getHeader("Referer");

		return "redirect:" + header;
	}
	
	
//	희성 작성 끝
}

//
