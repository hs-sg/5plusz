package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.dto.PostCreateDto;
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

		List<Post> list = postService.read();
		model.addAttribute("posts", list);

	}
	@GetMapping("/create")
	public void create() {
		log.debug(" GET create()");
	}
	@PostMapping("/create")
	public String create(PostCreateDto dto, @RequestParam("file") MultipartFile file) {
		log.debug("POST create(dto={}, file={})", dto, file);

		int result = postService.create(dto, file);
		if (result > 0) {
			return "redirect:/post/list";
		} else {
			return "error";
		}
	}
}
