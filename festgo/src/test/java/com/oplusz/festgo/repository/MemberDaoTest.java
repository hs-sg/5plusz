package com.oplusz.festgo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.MemberSelectJoinRequestDto;
import com.oplusz.festgo.dto.MemberSelectJoinRoleDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class MemberDaoTest {

	@Autowired
	private MemberDao memberDao;
	
	
	
	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	// @Test
	public void testSelectMemberJoinRoleByUsername() {
		MemberSelectJoinRoleDto member = memberDao.selectMemberJoinRoleByUsername("admin");
		log.debug(member.toString());
		
		Assertions.assertNotNull(member);
	}
	
	// @Test
	public void testDeleteMemberByUsername() {
		Integer sponRequestDeleteResult = memberDao.deleteSponRequestByUsername(null);
	} //-> 아직 미완서 멤버 삭제하려면 다른 테이블에서 삭제할 것들이 너무 많음.
	
	// @Test
	public void testselectMemberJoinSponRequestBySrApproval() {
		List<MemberSelectJoinRequestDto> sponsors = memberDao.selectMemberJoinSponRequestBySrApproval();
		log.debug(sponsors.toString());
		
		Assertions.assertNotNull(sponsors);
	}
	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
