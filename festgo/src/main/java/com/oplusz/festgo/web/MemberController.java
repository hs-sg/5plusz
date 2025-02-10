package com.oplusz.festgo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.domain.SponRequest;
import com.oplusz.festgo.dto.MemberSignInDto;
import com.oplusz.festgo.dto.MemberSignUpDto;
import com.oplusz.festgo.service.MemberService;
import com.oplusz.festgo.service.MyPageService;
import com.oplusz.festgo.service.SponRequestService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final SponRequestService sponRequestService;
	private final MyPageService mypageService;
	
	// 로그인
	@PostMapping("/signin")
	@ResponseBody
	public ResponseEntity<Integer> signIn(@RequestBody MemberSignInDto dto, HttpSession session) {
		log.debug("signIn(dto={})", dto);
		
		Member memberForCheck = memberService.read(dto.getMeUsername());
		SponRequest sr = sponRequestService.read(memberForCheck.getMeId());
		if (sr.getSrApproval() == 1) {
			Member member = memberService.read(dto);	
			if (member == null) {
				// username과 password가 일치하는 사용자가 DB에 없는 경우 - 로그인 실패.
				return ResponseEntity.ok(0);
			} else {
				session.setAttribute("signedInUser", member.getMeUsername());
				session.setAttribute("memberRole", member.getMrId());
				return ResponseEntity.ok(1);
			}
		} else { // srApproval의 값이 1(승인)이 아닌 경우 - 로그인 실패.
			return ResponseEntity.ok(2);
		}
	}
	
	// 로그아웃
	@GetMapping("/signout")
	public String signOut(HttpSession session) {
		log.debug("signOut()");
		
		// 로그아웃 - 세션에 저장된 로그인 정보를 지움. 세션을 무효화(invalidate).
		session.removeAttribute("signedInUser");
		session.removeAttribute("role");
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
		int approval = 1;
		// 사업자 계정 등록요청은 관리자의 승인 필요: 0(대기중)
		if(dto.getMrId() == 2) approval = 0;
		
		memberService.create(dto, approval);
		
		// 회원가입 성공 후 홈페이지로 redirect
		return "redirect:/";
	}
	
	// username 중복체크
	// 중복되지 않은 username이면 "Y", 중복된 username이면 "N" 리턴
	@GetMapping("/checkusername")
	@ResponseBody
	public ResponseEntity<String> checkUsername(@RequestParam String username) {
		log.debug("GET checkUsername(username={})", username);
		
		boolean result = memberService.checkUsername(username);
		if(result) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}		
	}
	
	// email 중복체크
	// 중복되지 않은 email이면 "Y", 중복된 email이면 "N" 리턴
	@GetMapping("/checkemail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(@RequestParam String email) {
		log.debug("GET checkEmail(email={})", email);
		
		boolean result = memberService.checkEmail(email);
		if(result) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}	
	}
	
	// sponsor 중복체크
	// 중복되지 않은 sponsor이면 "Y", 중복된 sponsor이면 "N" 리턴
	@GetMapping("/checksponsor")
	@ResponseBody
	public ResponseEntity<String> checkSponsor(@RequestParam String sponsor) {
		log.debug("GET checkSponsor(sponsor={})", sponsor);
		
		boolean result = memberService.checkSponsor(sponsor);
		if(result) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}	
	}
	
	
}
