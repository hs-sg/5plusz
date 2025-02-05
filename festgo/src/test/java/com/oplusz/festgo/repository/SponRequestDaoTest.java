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
public class SponRequestDaoTest {

	@Autowired
	private SponRequestDao sponRequestDao;
	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	
	// @Test
	public void testaApproveSponsorMemberByMeId() {
		Integer approveResult = sponRequestDao.approveSponsorMemberByMeId(61);
		log.debug(approveResult.toString());
		Assertions.assertEquals(approveResult, 1);
	}
	
	// @Test
	public void testRefuseSponsorMemberByMeId() {
		Integer refuseResult = sponRequestDao.refuseSponsorMemberByMeId("거절됐습니다", 61);
		log.debug(refuseResult.toString());
		Assertions.assertEquals(refuseResult, 1);
	}
	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
