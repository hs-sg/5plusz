package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.repository.MemberDao;
import com.oplusz.festgo.repository.PostDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

	private final MemberDao memberDao;
	private final PostDao postDao;
	
//	public Members readByUsername(String username) {
//		log.debug("readByUsername(username={})", username);
//	}
}
