package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oplusz.festgo.domain.Posts;
import com.oplusz.festgo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.debug("list()");
		
		List<Posts> list = postService.read();
		model.addAttribute("posts",list);
		
	}
}
