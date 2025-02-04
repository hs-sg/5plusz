package com.oplusz.festgo.repository;

import org.apache.ibatis.annotations.Param;

public interface FestRequestDao {
	
	int insertFestRequest(int feId, int frApproval);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	Integer approveFestivalByFeId(@Param("feId") Integer feId);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
}
