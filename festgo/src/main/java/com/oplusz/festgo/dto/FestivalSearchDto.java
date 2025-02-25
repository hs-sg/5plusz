package com.oplusz.festgo.dto;

import lombok.Data;

@Data
public class FestivalSearchDto {
	private String month;
	private Integer lcId;
	private Integer theId;
	private String keyword;
	private Integer startIndexNum;
}
