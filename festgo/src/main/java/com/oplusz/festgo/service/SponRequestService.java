package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.SponRequest;
import com.oplusz.festgo.repository.SponRequestDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SponRequestService {
	private final SponRequestDao sponRequestDao;
	
	// 회원의 id로 회원가입요청 정보를 불러옴.
	public SponRequest read(int meId) {
		log.debug("read(meId={})", meId);
		
		SponRequest sr = sponRequestDao.selectByMeId(meId);
		
		return sr;
	}
	
}
