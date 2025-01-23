package com.oplusz.festgo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/mypage")
public class MyPageController {

	@GetMapping("/{username}")
	public void myPage() {
		log.debug("Get myPage()");
	}
}
