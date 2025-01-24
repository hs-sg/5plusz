package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.service.MyPageService;

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
		
		Integer mrId = myPageService.readRoleIdByUsername(username);
		
		List<FestivalSelectJoinLikesDto> list = myPageService.readFestivalUserInMyPage(mrId);
		
		return ResponseEntity.ok(list);
	}
}
