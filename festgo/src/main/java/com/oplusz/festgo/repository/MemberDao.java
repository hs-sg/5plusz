package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.Member;

public interface MemberDao {
	Member selectByUsernameAndPassword(Member member);
}
