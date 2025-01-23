package com.oplusz.festgo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.service.FestivalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/fest")
public class FestivalController {
	
	// 축제 서비스 생성
	private final FestivalService festivalService;

	// GET 방식 매핑
	@GetMapping("/create")
	public void create() {
		log.debug("GET create()");
	}
	
	// POST 방식 매핑 저장 후 홈으로 리턴
	@PostMapping("/create")
	public String create(FestivalCreateDto dto) {
		
		festivalService.create(dto);
		
		return "redirect:/";
	}

}
