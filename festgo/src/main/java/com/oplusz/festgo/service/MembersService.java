package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Members;
import com.oplusz.festgo.dto.MembersSignInDto;
import com.oplusz.festgo.repository.MembersDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembersService {
	private final MembersDao membersDao;
	
	// 입력한 아이디(username), 비밀번호(password)와 동일한 값을 갖는 행이 
	// DB의 Members 테이블에 존재한다면 Members 객체로 리턴.
	public Members read(MembersSignInDto dto) {
		log.debug("read(dto={})", dto);
		
		Members signedInMember = membersDao.selectByUsernameAndPassword(dto.toEntity());
		
		return signedInMember;
	}
}
