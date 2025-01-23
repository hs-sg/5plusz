package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSignInDto;
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
}
