package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.FestRequest;

public interface FestRequestDao {
	
	int insertFestRequest(int feId, int frApproval);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	int approveFestivalByFeId(int feId);
	int refuseFestivalByMeId(String frCause, int feId);
	int countFestivalByFrApproval(int frApproval);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
	int selectFrIdByFeId(int feId);

	

	FestRequest selectFestRequestByFrId(int frId);
}


