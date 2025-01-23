package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.Members;

public interface MembersDao {
	Members selectByUsernameAndPassword(Members members);
}
