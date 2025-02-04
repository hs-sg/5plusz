package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.SponRequest;

public interface SponRequestDao {
	int insertSponRequest(int meId, int srApproval);
	SponRequest selectByMeId(int meId);
}
