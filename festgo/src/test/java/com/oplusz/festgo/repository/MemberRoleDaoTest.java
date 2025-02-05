package com.oplusz.festgo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class MemberRoleDaoTest {

	@Autowired
	private MemberRoleDao memberRoleDao;
	
	
	
	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	// @Test
	public void testSelectMrIdByUsername() {
		Integer mrId = memberRoleDao.selectMrIdByUsername("admin");
		log.debug(mrId.toString());
		Assertions.assertEquals(mrId, 3);
	}
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
