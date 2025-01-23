package com.oplusz.festgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oplusz.festgo.domain.Members;

import lombok.Data;


@Data
public class MembersSignInDto {
	@JsonProperty("mUsername")
	private String mUsername;
	@JsonProperty("mPassword")
	private String mPassword;
	
	public Members toEntity() {
		return Members.builder().mUsername(mUsername).mPassword(mPassword).build();
	}
}
