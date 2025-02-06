package com.oplusz.festgo.repository;

import org.apache.ibatis.annotations.Param;

import com.oplusz.festgo.domain.SponRequest;

public interface SponRequestDao {

	int insertSponRequest(int meId, int srApproval);
	SponRequest selectByMeId(int meId);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	Integer approveSponsorMemberByMeId(Integer meId);
	Integer refuseSponsorMemberByMeId(@Param("srCause") String srCause, @Param("meId") Integer meId);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
}
