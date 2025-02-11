package com.oplusz.festgo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oplusz.festgo.domain.Alarm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class AlarmDaoTest {

	@Autowired
	private AlarmDao alarmDao;
	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	
	// @Test
	public void testInsertRequest() {
		Alarm alarm = Alarm.builder().alSfid(3).meId(41).alStatus(0).build();
		log.debug("alarm = {}", alarm.toString());
		
		Integer insertResult = alarmDao.insertRequest(alarm);
		Assertions.assertEquals(insertResult, 1);
	}
	
	// @Test
	public void testUpdateProcess() {
		Integer updateResult = alarmDao.updateProcess(1, 1);
		Assertions.assertEquals(updateResult, 1);
	}
	
	@Test
	public void testCheckProcess() {
		Integer updateResult = alarmDao.updateCheck(1, 1);
		Assertions.assertEquals(updateResult, 1);
	}

	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
