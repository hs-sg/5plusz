package com.oplusz.festgo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.service.FestivalService;
import com.oplusz.festgo.service.MemberService;
import com.oplusz.festgo.service.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final MyPageService myPageService;
	
	@GetMapping("/mypage")
	public void myPage(Model model, HttpSession session) {
		log.debug("get myPage()");
		
		String meUsername = session.getAttribute("signedInUser").toString();
		MemberSelectJoinRoleDto member = myPageService.readMemberInMyPage(meUsername);
		model.addAttribute("member", member);
	}
}
