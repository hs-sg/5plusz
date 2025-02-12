package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.SponRequest;

public interface SponRequestDao {

	int insertSponRequest(int meId, int srApproval);
	SponRequest selectByMeId(int meId);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	Integer approveSponsorMemberByMeId(Integer meId);
	Integer refuseSponsorMemberByMeId(String srCause, Integer meId);
	Integer countSponsorBySrApproval(Integer srApproval);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------

}

