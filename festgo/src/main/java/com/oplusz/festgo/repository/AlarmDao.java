package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.Alarm;

public interface AlarmDao {
	
// 희성 작성 시작
	
	Integer insertRequest(Alarm alarm);
	Integer updateProcess(Integer alCategory, Integer alSfid);
	Integer updateCheck(Integer alCategory, Integer alSfid);
	
// 희성 작성 끝
	
}