package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSignInDto;
import com.oplusz.festgo.dto.MemberSignUpDto;
import com.oplusz.festgo.repository.MemberDao;
import com.oplusz.festgo.repository.SponRequestDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDao memberDao;
	private final SponRequestDao sponRequestDao;
	private final AlarmService alarmService;
	
	// 입력한 아이디(username), 비밀번호(password)와 동일한 값을 갖는 행이 
	// DB의 Member 테이블에 존재한다면 Member 객체로 리턴.
	public Member read(MemberSignInDto dto) {
		log.debug("read(dto={})", dto);
		
		Member signedInMember = memberDao.selectByUsernameAndPassword(dto.toEntity());
		
		return signedInMember;
	}
	
	// username으로 검색한 결과를 Member 객체로 리턴
	public Member read(String username) {
		log.debug("read(username={})", username);
		
		Member member = memberDao.selectByUsername(username);
		
		return member;
	}
	
	
	// 회원가입
	public int create(MemberSignUpDto dto, int approval) {
		log.debug("create(dto={}, approval={})", dto, approval);
		
		int result = memberDao.insertMember(dto.toEntity());
		
		// spon_request 테이블에 회원가입을 요청한 회원의 id와 승인여부를 입력.
		int meId = memberDao.selectByUsername(dto.getMeUsername()).getMeId();
		int srApproval = approval;
		log.debug("insertSponRequest(meId={}, srApproval={})", meId, srApproval);
		sponRequestDao.insertSponRequest(meId, srApproval);
		
		alarmService.create(dto.getMeUsername()); //-> 알람 추가
		
		return result;
	}

	// 입력한 username이 Member 테이블에 없으면 true, 이미 있으면 false를 리턴.
	public boolean checkUsername(String username) {
		log.debug("checkUsername(username={})", username);
		
		if (memberDao.selectByUsername(username) == null) return true;
		
		return false;
	}

	// 입력한 email이 Member 테이블에 없으면 true, 이미 있으면 false를 리턴.
	public boolean checkEmail(String email) {
		log.debug("checkEmail(email={})", email);
		
		if (memberDao.selectByEmail(email) == null) return true;
		
		return false;
	}
	
	// 입력한 sponsor가 Member 테이블에 없으면 true, 이미 있으면 false를 리턴.
	public boolean checkSponsor(String sponsor) {
		log.debug("checkSponsor(sponsor={}", sponsor);
		
		if (memberDao.selectBySponsor(sponsor) == null) return true;
		
		return false;
	}
}

//
