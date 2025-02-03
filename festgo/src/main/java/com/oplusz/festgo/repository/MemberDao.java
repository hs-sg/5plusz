package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;

public interface MemberDao {
	Member selectByUsernameAndPassword(Member member);
	int insertMember(Member member);
	int insertMemberForBusiness(Member member); 
	Member selectByUsername(String username);
	Member selectByEmail(String email);
	Member selectBySponsor(String sponsor);
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	MemberSelectJoinRoleDto selectMemberJoinRoleByUsername(String meUsername);
	Integer deleteMemberByUsername(String meUsername);
	Integer deleteSponRequestByUsername(String meUsername);
	List<Member> selectMemberJoinSponRequestBySrApproval();
	
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
}
