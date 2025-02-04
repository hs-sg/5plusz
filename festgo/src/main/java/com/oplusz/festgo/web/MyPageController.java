package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.dto.SponRequestRefuseDto;
import com.oplusz.festgo.service.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

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
	public ResponseEntity<List<MemberSelectJoinRequestDto>> getSponsorMemberByApproval() {
		log.debug("getMemberByApproval()");
		
		List<MemberSelectJoinRequestDto> requestSponsors = myPageService.readRequestSponsorInMyPage();
		
		return ResponseEntity.ok(requestSponsors);
	}
	
	// 마이페이지 상에 대기중인 축제 승인하기
	@GetMapping("/festapp/{feId}")
	public ResponseEntity<Integer> approveFestival(@PathVariable("feId") Integer feId) {
		log.debug("approveFestival(feId={})", feId);
		
		Integer approveResult = myPageService.approveFestivalByFeId(feId);
		log.debug("approveResult = {}", approveResult);
		
		return ResponseEntity.ok(approveResult);
	}
	
	// 마이페이지 상에 대기중인 사업자 아이디 승인하기
	@GetMapping("/sponapp/{meId}")
	public ResponseEntity<Integer> approveSponsorMember(@PathVariable("meId") Integer meId) {
		log.debug("approveSponsorMember(meId={})", meId);
		
		Integer approveResult = myPageService.approveSponsorMemberByMeId(meId);
		log.debug("approveResult = {}", approveResult);
		
		return ResponseEntity.ok(approveResult);
	}
	
	// 마이페이지 상에 대기중인 사업자 아이디 거절하기
	@PutMapping("/sponref/")
	@ResponseBody
	public ResponseEntity<Integer> refuseSponsorMember(@RequestBody SponRequestRefuseDto dto)
	{
		log.debug("refuseSponsorMember(dto={})", dto);
		
		Integer meId = dto.getMeId();
		String srCause = dto.getSrCause();
		
		Integer refuseResult = myPageService.refuseSponsorMemberByMeId(meId, srCause);
		log.debug("refuseResult = {}", refuseResult);
		
		return ResponseEntity.ok(refuseResult);
	}
}
