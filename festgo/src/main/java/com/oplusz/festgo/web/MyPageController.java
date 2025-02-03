package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.service.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@RestController
public class MyPageController {

	private final MyPageService myPageService;
	
	// 프로필 정보 가져오기
	@GetMapping("/profile/{signedInUser}")
	public ResponseEntity<MemberSelectJoinRoleDto> getMemberByUsername(@PathVariable("signedInUser") String username) {
		log.debug("getMemberByUsername(username={})", username);
		
		MemberSelectJoinRoleDto member = myPageService.readMemberInMyPage(username);
		
		return ResponseEntity.ok(member);
	}
	
	// 마이페이지 상에 유저가 좋아요한 축제 리스트 가져오기
	@GetMapping("/ufestivals/{signedInUser}")
	public ResponseEntity<List<FestivalSelectJoinLikesDto>> getFestivalsByUsername(@PathVariable("signedInUser") String username) {
		log.debug("getFestivalsByUsername(username={}", username);
		
		Integer meId = myPageService.readMeIdByUsername(username);
		log.debug("getFestivalsByUsername(meId={}", meId);
		
		List<FestivalSelectJoinLikesDto> festivals = myPageService.readFestivalUserInMyPage(meId);
		
		return ResponseEntity.ok(festivals);
	}
	
	// 마이페이지 상에 스폰서가 등록한 축제 리스트 가져오기
	@GetMapping("/sfestivals/{signedInUser}")
	public ResponseEntity<List<FestivalSelectJoinRequestDto>> getFestivalsBySponsor(@PathVariable("signedInUser") String username) {
		log.debug("getFestivalsByUsername(username={}", username);
		
		String sponsor = myPageService.readSponsorByUsername(username);
		log.debug("getFestivalsByUsername(sponsor={}", sponsor);
		
		List<FestivalSelectJoinRequestDto> festivals = myPageService.readFestivalSponsorInMyPage(sponsor);
		
		return ResponseEntity.ok(festivals);
	}
	
	// 마이페이지 상에 관리자가 볼 모든 축제 리스트 가져오기
	@GetMapping("/afestivals/")
	public ResponseEntity<List<FestivalSelectJoinRequestDto>> getAllFestivals() {
		log.debug("getAllFestivals()");
		
		List<FestivalSelectJoinRequestDto> festivals = myPageService.readFestivalAdminInMyPage();
		
		return ResponseEntity.ok(festivals);
	}
	
	// 마이페이지 상에 관리자가 볼 승인 대기중인 사업자 아이디 리스트 가져오기
	@GetMapping("/sponcheck/")
	public ResponseEntity<List<Member>> getSponsorMemberByApproval() {
		log.debug("getMemberByApproval()");
		
		List<Member> requestSponsors = myPageService.readRequestSponsorInMyPage();
		
		return ResponseEntity.ok(requestSponsors);
	}
}
