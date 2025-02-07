package com.oplusz.festgo.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oplusz.festgo.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class PostDaoTest {

	@Autowired
	private PostDao postDao;

//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	
	// @Test
	public void testreadByMeUsername() {
		List<Post> list = postDao.readByMeUsername("ㅇ");
		log.debug(list.toString());
		Assertions.assertNotNull(list);
	}
	
	// @Test
	public void testreadTen() {
		List<Post> list = postDao.readVariable(1, 10);
		log.debug(list.toString());
		Assertions.assertNotNull(list);
	}
	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
