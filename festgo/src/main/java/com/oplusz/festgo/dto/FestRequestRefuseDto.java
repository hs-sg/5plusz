package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.FestRequest;

import lombok.Data;


@Data
public class FestRequestRefuseDto {
	private Integer feId;
	private String frCause;
	
	public FestRequest toEntity() {
		return FestRequest.builder().feId(feId).frCause(frCause).build();
	}
}
