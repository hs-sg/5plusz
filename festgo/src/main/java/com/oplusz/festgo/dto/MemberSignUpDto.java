package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.Member;

import lombok.Data;

@Data
public class MemberSignUpDto {
	private String meUsername;
	private String mePassword;
	private String meEmail;
	private String meSponsor;
	private Integer mrId;
	
	public Member toEntity() {
		Member member = Member.builder()
				.meUsername(meUsername).mePassword(mePassword).meEmail(meEmail)
				.mrId(mrId)
				.build();
		if (meSponsor != null) member.setMeSponsor(meSponsor);
		
		return member; 
	}

}
