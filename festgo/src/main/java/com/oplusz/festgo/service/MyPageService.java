package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.repository.FestivalDao;
import com.oplusz.festgo.repository.MemberDao;
import com.oplusz.festgo.repository.MemberRoleDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

	private final FestivalDao festivalDao;
	private final MemberDao memberDao;
	private final MemberRoleDao memberRoleDao;
	
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
	
	// fest_request와 조인된 테이블에서 사업자가 개최한 축제 읽기 -> 마이페이지 사업자에서 사용
	public List<FestivalSelectJoinRequestDto> readFestivalSponsorInMyPage(String meSponsor) {
		log.debug("readFestivalSponsorInMyPage()");
		
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestBySponsor(meSponsor);
		log.debug("result list={}", list);
		
		return list;
	}
	
	// Likes와 조인된 테이블에서 일반유저가 좋아요한 축제 읽기 -> 마이페이지 일반유저에서 사용
	public List<FestivalSelectJoinLikesDto> readFestivalUserInMyPage(Integer meId) {
		log.debug("readFestivalUserInMyPage(meId={})", meId);
		
		List<FestivalSelectJoinLikesDto> list = festivalDao.selectFestivalJoinLikesByMemberId(meId);
		log.debug("result list={}", list);
		
		return list;
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
	
	public Integer readMeIdByUsername(String meUsername) {
		log.debug("readSponsorByUsername(meUsername={})", meUsername);
		
		Integer meId = memberDao.selectMemberJoinRoleByUsername(meUsername).getMeId();
		log.debug("result meId={}", meId);
		
		return meId;
	}
}
