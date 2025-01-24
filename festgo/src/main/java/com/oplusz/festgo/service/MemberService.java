package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSignInDto;
import com.oplusz.festgo.dto.MemberSignUpDto;
import com.oplusz.festgo.repository.MemberDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDao memberDao;
	
	// 입력한 아이디(username), 비밀번호(password)와 동일한 값을 갖는 행이 
	// DB의 Members 테이블에 존재한다면 Members 객체로 리턴.
	public Member read(MemberSignInDto dto) {
		log.debug("read(dto={})", dto);
		
		Member signedInMember = memberDao.selectByUsernameAndPassword(dto.toEntity());
		
		return signedInMember;
	}
	
	// 회원가입
	public int create(MemberSignUpDto dto) {
		log.debug("create(dto={})", dto);
		int result = 0;
		
		// 사업자 회원인 경우
		if(dto.getMeSponsor() != null) {
			result = memberDao.insertMemberForBusiness(dto.toEntity());
			return result;
		} 
		// 일반/관리자 회원인 경우
		result = memberDao.insertMember(dto.toEntity());	
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
