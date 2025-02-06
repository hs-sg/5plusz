package com.oplusz.festgo.web;

import java.util.Enumeration;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.dto.FestRequestRefuseDto;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.dto.MemberSignInDto;
import com.oplusz.festgo.dto.SponRequestRefuseDto;
import com.oplusz.festgo.service.MyPageService;
import com.oplusz.festgo.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@RestController
public class MyPageController {

	private final MyPageService myPageService;
	private final PostService postService;
	
	// 프로필 정보 가져오기
	@GetMapping("/profile/{signedInUser}")
	public ResponseEntity<MemberSelectJoinRoleDto> getMemberByUsername(@PathVariable("signedInUser") String username) {
		log.debug("getMemberByUsername(username={})", username);
		
		MemberSelectJoinRoleDto member = myPageService.readMemberInMyPage(username);
		
		return ResponseEntity.ok(member);
	}
	
	// 비밀번호 변경하기
	@PutMapping("/chapass")
	@ResponseBody
	public ResponseEntity<Integer> changePasswordByUsername(@RequestBody MemberSignInDto dto){
		log.debug("updateMePasswordByUsername(dto= {})", dto);
		
		String meUsername = dto.getMeUsername();
		String mePassword = dto.getMePassword();
		
		Integer updatePasswordResult = myPageService.updatePasswordByUsername(meUsername, mePassword);
		log.debug("updatePasswordResult = {}", updatePasswordResult);
		
		return ResponseEntity.ok(updatePasswordResult);
	}
	
	// 로그인된 멤버 탈퇴하기
	@DeleteMapping("/delmem/{signedInUser}")
	public ResponseEntity<Integer> deleteMemberByUsername(@PathVariable("signedInUser") String meUsername) {
		log.debug("deleteMemberByUsername(meUsername= {})", meUsername);
		
		Integer deleteMemberResult = myPageService.deleteMemberByUsername(meUsername);
		log.debug("deleteMemberResult = {}", deleteMemberResult);
		
		return ResponseEntity.ok(deleteMemberResult);
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
	
	// 마이페이지 상에 로그인한 유저, 스폰서가 등록한 글 목록 가져오기
//	@GetMapping("/usposts/{signedInUser}")
//	public ResponseEntity<List<Post>> getWrittenPostsByMember(@PathVariable("signedInUser") String username) {
//		log.debug("getWrittenPostsByMember(username={})", username);
//		
//		List<Post> posts = postService.readByMeUsername(username);
//		
//		return ResponseEntity.ok(posts);
//	}
	
	// 마이페이지 상에 관리자가 볼 전체 글 목록 가져오기
	@GetMapping("/aposts/")
	public ResponseEntity<List<Post>> getAllPosts() {
		log.debug("getAllPosts()");
		
		List<Post> posts = postService.read();
		
		return ResponseEntity.ok(posts);
	}
	
	// 마이페이지 상에 관리자가 볼 전체 글 목록 페이지 마다 원하는 개수로 가져오기
	@GetMapping("/aposts/{pagenum}")
	public ResponseEntity<List<Post>> getVarialbePostsByPageNum(@PathVariable("pagenum") String pageNum) {
		log.debug("getVarialbePostsByPageNum(pageNum={})", pageNum);
		
		List<Post> posts = postService.readVariableByPageNum(10, Integer.parseInt(pageNum));
		
		return ResponseEntity.ok(posts);
	}
	
	// 마이페이지 상에 유저, 스폰서가 볼 작성 글목록 페이지 마다 원하는 개수로 가져오기
	@GetMapping("/usposts/{pagenum}")
	public ResponseEntity<List<Post>> getVarialbePostsByPageNumAndUsername(@PathVariable("pagenum") String pageNum, HttpSession session) {
		String username = session.getAttribute("signedInUser").toString();
		log.debug("getVarialbePostsByPageNumAndUsername(pageNum={}, username={})", pageNum, username);
		List<Post> posts = postService.readVariableByPageNumAndUsername(10, Integer.parseInt(pageNum), username);
		
		return ResponseEntity.ok(posts);
	}
	
	//  마이페이지 상에 전체 글 갯수 가져오기
	@GetMapping("/cntaposts/")
	public ResponseEntity<Integer> getCountAllPosts() {
		log.debug("getCountAllPosts()");
		Integer countPosts = postService.countAllPosts();
		
		return ResponseEntity.ok(countPosts);
	}
	
	//  마이페이지 상에 유저나 사업자가 등록한 갯수 가져오기
	@GetMapping("/cntaposts/{signedInUser}")
	public ResponseEntity<Integer> getCountAllPostsByUsername(@PathVariable("signedInUser") String username) {
		log.debug("getCountAllPostsByUsername()");
		Integer countPosts = postService.getCountAllPostsByUsername(username);
		
		return ResponseEntity.ok(countPosts);
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
	
	// 마이페이지 상에 대기중인 축제 거절하기
	@PutMapping("/festref/")
	@ResponseBody
	public ResponseEntity<Integer> refuseFestival(@RequestBody FestRequestRefuseDto dto)
	{
		log.debug("refuseFestival(dto={})", dto);
		
		Integer feId = dto.getFeId();
		String frCause = dto.getFrCause();
		
		Integer refuseResult = myPageService.refuseFestivalByFeId(feId, frCause);
		log.debug("refuseResult = {}", refuseResult);
		
		return ResponseEntity.ok(refuseResult);
	}
	
	// 마이페이지 상에 축제 삭제하기
	@DeleteMapping("/festdel/{feId}")
	public ResponseEntity<Integer> deleteFestival(@PathVariable("feId") Integer feId) {
		log.debug("deleteFestival(feId={})", feId);
		
		Integer deleteResult = myPageService.deleteFestivalByFeId(feId);
		log.debug("approveResult = {}", deleteResult);
		
		return ResponseEntity.ok(deleteResult);
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
