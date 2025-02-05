package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.SponRequest;

import lombok.Data;


@Data
public class SponRequestRefuseDto {
	private Integer meId;
	private String srCause;
	
	public SponRequest toEntity() {
		return SponRequest.builder().meId(meId).srCause(srCause).build();
	}
}
