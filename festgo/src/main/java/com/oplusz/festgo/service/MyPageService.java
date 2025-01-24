package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;
import com.oplusz.festgo.repository.FestivalDao;
import com.oplusz.festgo.repository.MemberDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

	private final FestivalDao festivalDao;
	private final MemberDao memberDao;
	
	//	전체 축제목록 읽기
	public List<Festival> readFestivalInMyPage() {
		log.debug("readFestivalInMyPage()");
		
		List<Festival> list = festivalDao.selectFestivalAll();
		log.debug("readFestivalInMyPage(list={})", list);

		return list;
	}

	// fest_request와 조인된 전체 축제 읽기 -> 마이페이지 관리자에서 사용
	public List<FestivalSelectJoinRequestDto> readFestivalAdminInMyPage() {
		log.debug("readFestivalAdminInMyPage()");
		
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestAll();
		log.debug("readFestivalAdminInMyPage(list={})", list);
		
		return list;
	}
	
	// fest_request와 조인된 전체 축제 읽기 -> 마이페이지 사업자에서 사용
	public List<FestivalSelectJoinRequestDto> readFestivalSponsorInMyPage(String meSponsor) {
		log.debug("readFestivalSponsorInMyPage()");
		
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestBySponsor(meSponsor);
		log.debug("readFestivalSponsorInMyPage(list={})", list);
		
		return list;
	}
	
	// Likes와 조인된 전체 축제 읽기 -> 마이페이지 일반유저에서 사용
	public List<FestivalSelectJoinLikesDto> readFestivalUserInMyPage(Integer meId) {
		log.debug("readFestivalUserInMyPage()");
		
		List<FestivalSelectJoinLikesDto> list = festivalDao.selectFestivalJoinLikesByMemberId(meId);
		log.debug("readFestivalUserInMyPage(list={})", list);
		
		return list;
	}
	
	public MemberSelectJoinRoleDto readMemberInMyPage(String meUsername) {
		log.debug("readMemberInMyPage()");
		
		MemberSelectJoinRoleDto member = memberDao.selectMemberJoinRoleByUsername(meUsername);
		log.debug("readMemberInMyPage(member={})", member);
		
		return member;
	}
	
	
}
