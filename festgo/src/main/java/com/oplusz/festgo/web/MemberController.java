package com.oplusz.festgo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSignInDto;
import com.oplusz.festgo.dto.MemberSignUpDto;
import com.oplusz.festgo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	// 로그인
	@PostMapping("/signin")
	@ResponseBody
	public ResponseEntity<Integer> signIn(@RequestBody MemberSignInDto dto, HttpSession session) {
		
		log.debug("signIn(dto={})", dto);
		
		Member member = memberService.read(dto);
		if (member == null) {
			// username과 password가 일치하는 사용자가 DB에 없는 경우 - 로그인 실패.
			return ResponseEntity.ok(0);
		} else {
			// username과 password가 일치하는 사용자가 DB에 있는 경우 - 로그인 성공.
			session.setAttribute("signedInUser", member.getMeUsername());
		}
		return ResponseEntity.ok(1);
	}
	
	// 로그아웃
	@GetMapping("/signout")
	public String signOut(HttpSession session) {
		log.debug("signOut()");
		
		// 로그아웃 - 세션에 저장된 로그인 정보를 지움. 세션을 무효화(invalidate).
		session.removeAttribute("signedInUser");
		session.invalidate();
		
		// 로그아웃 이후에 홈페이지로 이동(redirect)
		return "redirect:/";
	}
	
	// 회원가입 페이지
	@GetMapping("/signup")
	public void signUp() {
		log.debug("GET signUp()");
	}
	
	// 새로운 회원 등록
	@PostMapping("/signup")
	public String signUp(MemberSignUpDto dto) {
		log.debug("POST signUp(dto={})", dto);
		
		memberService.create(dto);
		
		// 회원가입 성공 후 홈페이지로 redirect
		return "redirect:/";
	}
}
