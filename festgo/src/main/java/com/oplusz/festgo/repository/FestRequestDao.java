package com.oplusz.festgo.repository;

public interface FestRequestDao {
	
	int insertFestRequest(int feId, int frApproval);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	int approveFestivalByFeId(int feId);
	int refuseFestivalByMeId(String frCause, int feId);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
}
