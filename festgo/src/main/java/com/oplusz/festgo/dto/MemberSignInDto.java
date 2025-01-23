package com.oplusz.festgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oplusz.festgo.domain.Member;

import lombok.Data;


@Data
public class MemberSignInDto {
	private String meUsername;
	private String mePassword;
	
	public Member toEntity() {
		return Member.builder().meUsername(meUsername).mePassword(mePassword).build();
	}
}
