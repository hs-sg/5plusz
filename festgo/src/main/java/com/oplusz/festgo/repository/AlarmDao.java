package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Alarm;

public interface AlarmDao {
	
// 희성 작성 시작
	
	Integer insertRequest(Alarm alarm);
	Integer updateProcess(Integer alCategory, Integer alSfid);
	Integer updateCheck(Integer alCategory, Integer alSfid);
	List<Alarm> selectAllByMeId(Integer meId);
	// each : 가져올 갯수, number : 몇번째
	// ex) number 2, each 5 이면 11~15번 가져오기
	List<Alarm> selectEachByMeId(Integer number, Integer each, Integer meId);
	Integer countByMeId(Integer meId);
	
	
// 희성 작성 끝
	
}
