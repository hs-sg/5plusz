package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.repository.PostDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service

public class PostService {

	private final PostDao postDao;

	public List<Post> read() {
		log.debug("read()");
		List<Post> list = postDao.selectOrderByIdDesc();
		
		log.debug("Posts list: {}", list);
		return list;
	}
}
