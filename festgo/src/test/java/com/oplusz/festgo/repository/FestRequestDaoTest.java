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
public class FestRequestDaoTest {

	@Autowired
	private FestRequestDao festRequestDao;
	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	
	// @Test
	public void testApproveFestivalByMeId() {
		Integer approveResult = festRequestDao.approveFestivalByFeId(117);
		log.debug(approveResult.toString());
		Assertions.assertEquals(approveResult, 1);
	}
	
//	@Test
//	public void testRefuseSponsorMemberByMeId() {
//		Integer refuseResult = festRequestDao.refuseFestivalByMeId("거절됐습니다", 61);
//		log.debug(refuseResult.toString());
//		Assertions.assertEquals(refuseResult, 1);
//	}
	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
