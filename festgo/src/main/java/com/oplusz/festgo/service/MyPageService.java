package com.oplusz.festgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.repository.FestRequestDao;
import com.oplusz.festgo.repository.FestivalDao;
import com.oplusz.festgo.repository.LikesDao;
import com.oplusz.festgo.repository.MemberDao;
import com.oplusz.festgo.repository.MemberRoleDao;
import com.oplusz.festgo.repository.SponRequestDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

	private final FestivalDao festivalDao;
	private final MemberDao memberDao;
	private final MemberRoleDao memberRoleDao;
	private final SponRequestDao sponRequestDao;
	private final FestRequestDao festRequestDao;
	private final LikesDao likesDao;
	
	//	전체 축제목록 읽기
	public List<Festival> readFestivalInMyPage() {
		log.debug("readFestivalInMyPage()");
		
		List<Festival> list = festivalDao.selectFestivalAll();
		log.debug("result list={}", list);

		return list;
	}

	// fest_request와 조인된 전체 축제 읽기 -> 마이페이지 관리자에서 사용
	public List<FestivalSelectJoinRequestDto> readFestivalAdminInMyPage() {
		log.debug("readFestivalAdminInMyPage()");
		
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestAll();
		log.debug("result list={}", list);
		
		return list;
	}
	
	// fest_request와 조인된 전체 축제 eachNumber 만큼 읽기 -> 마이페이지 스폰서가 사용
	// category = [-1:전체, 0:게시중, 1:승인대기, 2:거절됨], eachNumber = 가져올 갯수, meUsername = 로그인된 아이디
	public List<FestivalSelectJoinRequestDto> readFestivalSponsorInMyPage
	(Integer eachNumber, Integer category, String meUsername) {
		log.debug("readFestivalAdminInMyPage(eachNumber={}, category={}, meUsername={})", eachNumber, category, meUsername);
		String meSponsor = readSponsorByUsername(meUsername);
		List<FestivalSelectJoinRequestDto> list = new ArrayList<>();
		switch(category) {
		case -1 :
			list = festivalDao.selectAllFestivalJoinRequestBySponsorEachNumber(eachNumber, meSponsor);
			break;
		case 0, 1, 2 :
			list = festivalDao.selectFestivalJoinRequestByFrApprovalAndSponsorEachNumber(category, eachNumber, meSponsor);
		}
		log.debug("result list={}", list);
		
		return list;
	}
	
	// fest_request와 조인된 전체 축제 eachNumber 만큼 읽기 -> 마이페이지 관리자에서 사용
	// category = [-1:전체, 0:게시중, 1:승인대기, 2:거절됨], eachNumber = 가져올 갯수
	public List<FestivalSelectJoinRequestDto> readFestivalAdminInMyPage(Integer eachNumber, Integer category) {
		log.debug("readFestivalAdminInMyPage(eachNumber={}, category={})", eachNumber, category);
		List<FestivalSelectJoinRequestDto> list = new ArrayList<>();
		switch(category) {
		case -1 :
			list = festivalDao.selectAllFestivalJoinRequestEachNumber(eachNumber);
			break;
		case 0, 1, 2 :
			list = festivalDao.selectFestivalJoinRequestByFrApprovalEachNumber(category, eachNumber);
		}
		log.debug("result list={}", list);
		
		return list;
	}
	
	// fest_request와 조인된 fr_approval로 분류된 축제 읽기 -> 마이페이지 관리자에서 사용
//	public List<FestivalSelectJoinRequestDto> readChoiceFestivalAdminInMyPage(Integer frApproval) {
//		log.debug("readWaitFestivalAdminInMyPage()");
//		
//		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestByFrApproval(frApproval);
//		log.debug("result list={}", list);
//		
//		return list;
//	}
	
	// fest_request와 조인된 테이블에서 사업자가 개최한 축제 읽기 -> 마이페이지 사업자에서 사용
//	public List<FestivalSelectJoinRequestDto> readFestivalSponsorInMyPage(String meSponsor) {
//		log.debug("readFestivalSponsorInMyPage(meSponsor={})", meSponsor);
//		
//		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestBySponsor(meSponsor);
//		log.debug("result list={}", list);
//		
//		return list;
//	}
	
	// fest_request와 조인된 테이블에서 사업자가 개최한 축제중 분류된 읽기 -> 마이페이지 사업자에서 사용
	public List<FestivalSelectJoinRequestDto> readChoiceFestivalAdminInMyPageBySponsor(Integer frApproval, String meSponsor) {
		log.debug("readChoiceFestivalAdminInMyPageBySponsor(frApproval={}, meSponsor={})", frApproval, meSponsor);
		
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestBySponsorAndFrApproval(frApproval, meSponsor);
		log.debug("result list={}", list);
		
		return list;
	}
	
//	// Likes와 조인된 테이블에서 일반유저가 좋아요한 축제 읽기 -> 마이페이지 일반유저에서 사용
//	public List<FestivalSelectJoinLikesDto> readFestivalUserInMyPage(Integer meId) {
//		log.debug("readFestivalUserInMyPage(meId={})", meId);
//		
//		List<FestivalSelectJoinLikesDto> list = festivalDao.selectFestivalJoinLikesByMemberId(meId);
//		log.debug("result list={}", list);
//		
//		return list;
//	}
	
	// Likes와 조인된 테이블에서 일반유저가 좋아요한 축제 읽기 -> 마이페이지 일반유저에서 사용
	public List<FestivalSelectJoinLikesDto> readFestivalUserInMyPage(Integer meId, Integer eachNumber) {
		log.debug("readFestivalUserInMyPage(meId={}, eachNumber={})", meId, eachNumber);
		
		List<FestivalSelectJoinLikesDto> list = festivalDao.selectFestivalJoinLikesByMemberIdEachNumber(meId, eachNumber);
		log.debug("result list={}", list);
		
		return list;
	}
	
	// 좋아요 지우기
	public Integer deleteLikeFestivalByFeId(Integer feId, String meUsername) {
		Integer meId = memberDao.selectByUsername(meUsername).getMeId();
		log.debug("deleteLikeFestivalByFeId(feId={}, meId={}", feId, meId);
		
		Integer delLikeResult = likesDao.deleteLikeByFeIdAndMeUsername(feId, meId);
		log.debug("delete Like result={}", delLikeResult);
		
		return delLikeResult;
	}
	
	// 좋아요 등록하기
	public Integer insertLikeFestivalByFeId(Integer feId, String meUsername) {
		Integer meId = memberDao.selectByUsername(meUsername).getMeId();
		log.debug("insertLikeFestivalByFeId(feId={}, meId={}", feId, meId);
		
		Integer insLikeResult = likesDao.insertLikeByFeIdAndMeUsername(feId, meId);
		log.debug("delete Like result={}", insLikeResult);
		
		return insLikeResult;
	}
	
	// member_role과 조인된 테이블에서 로그인된 아이디의 정보 가져오기
	public MemberSelectJoinRoleDto readMemberInMyPage(String meUsername) {
		log.debug("readMemberInMyPage(meUsername={})", meUsername);
		
		MemberSelectJoinRoleDto member = memberDao.selectMemberJoinRoleByUsername(meUsername);
		log.debug("result member={}", member);
		
		return member;
	}
	
	// 로그인된 아이디의 mrId 값 가져오기
	public Integer readRoleIdByUsername(String meUsername) {
		log.debug("readMrIdByUsername(meUsername={})", meUsername);
		
		Integer mrId = memberRoleDao.selectMrIdByUsername(meUsername);
		log.debug("result mrId={}", mrId);
		
		return mrId;
	}
	
	// 로그인된 아이디의 me_sponsor 값 가져오기
	public String readSponsorByUsername(String meUsername) {
		log.debug("readSponsorByUsername(meUsername={})", meUsername);
		
		String meSponsor = memberDao.selectMemberJoinRoleByUsername(meUsername).getMeSponsor();
		log.debug("result meSponsor={}", meSponsor);
		
		return meSponsor;
	}
	
	// 로그인된 아이디의 me_id 값 가져오기
	public Integer readMeIdByUsername(String meUsername) {
		log.debug("readSponsorByUsername(meUsername={})", meUsername);
		
		Integer meId = memberDao.selectMemberJoinRoleByUsername(meUsername).getMeId();
		log.debug("result meId={}", meId);
		
		return meId;
	}
	
	// feId로 축제 삭제하기
	public Integer deleteFestivalByFeId(Integer feId) {
		log.debug("deleteFestivalByFeId(feId={}", feId);
		
		Integer FestivalDelResult = festivalDao.deleteFestivalByFeId(feId);
		log.debug("FestivalDelResult={}", FestivalDelResult);
		
		return FestivalDelResult;
	}
	
	// srApproval로 승인 대기 중인 스폰서 아이디 리스트 가져오기
	public List<MemberSelectJoinRequestDto> readRequestSponsorInMyPage() {
		log.debug("readRequestSponsorInMyPage()");
		
		List<MemberSelectJoinRequestDto> requestSponsors = memberDao.selectMemberJoinSponRequestBySrApproval();
		log.debug("result requestSponsors = {}", requestSponsors);
		
		return requestSponsors;
	}
	
	// 측제 승인하기
	public Integer approveFestivalByFeId(Integer feId) {
		log.debug("approveFestivalByFeId(feId={}", feId);
		
		Integer appFestivalResult = festRequestDao.approveFestivalByFeId(feId);
		log.debug("result appFestivalResult = {}", appFestivalResult);
		
		return appFestivalResult;
	}
	
	// 축제 거절하기
	public Integer refuseFestivalByFeId(Integer feId, String frCause) {
		log.debug("refuseFestivalByFeId(feId={}", feId);
		
		Integer refFestivalResult = festRequestDao.refuseFestivalByMeId(frCause, feId);
		log.debug("result refFestivalResult = {}", refFestivalResult);
		
		return refFestivalResult;
	}
	
	// 스폰서 멤버 승인하기
	public Integer approveSponsorMemberByMeId(Integer meId) {
		log.debug("approveSponsorMemberByMeId(meId={}", meId);
		
		Integer appSponsorResult = sponRequestDao.approveSponsorMemberByMeId(meId);
		Integer updateSponsorRoleResult =  memberDao.updateSponsorRoleByMeId(meId);
		log.debug("appSponsorResult = {}, updateSponsorRoleResult = {}", appSponsorResult, updateSponsorRoleResult);
		
		return appSponsorResult;
	}
	
	// 스폰서 멤버 거절하기
	public Integer refuseSponsorMemberByMeId(Integer meId, String srCause) {
		log.debug("refuseSponsorMemberByMeId(meId={}", meId);
		
		Integer refSponsorResult = sponRequestDao.refuseSponsorMemberByMeId(srCause, meId);
		log.debug("result refSponsorResult = {}", refSponsorResult);
		
		return refSponsorResult;
	}
	
	// 로그인된 아이디에서 비밀번호 변경하기
	public Integer updatePasswordByUsername(String meUsername, String mePassword) {
		log.debug("updatePasswordByUsername(meUsername={}, mePassword={}", meUsername, mePassword);
		
		Integer updatePasswordResult = memberDao.updatePasswordByUsername(mePassword, meUsername);
		log.debug("result updatePasswordResult = {}", updatePasswordResult);
		
		return updatePasswordResult;
	}
	
	// 로그인된 아이디 삭제하기
	public Integer deleteMemberByUsername(String meUsername) {
		log.debug("deleteMemberByUsername(meUsername={}", meUsername);
		
		Integer deleteMemberResult = memberDao.deleteMemberByUsername(meUsername);
		log.debug("result deleteMemberResult = {}", deleteMemberResult);
		
		return deleteMemberResult;
	}
	
}