package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.Review;

import lombok.Data;

@Data
public class ReviewUpdateDto {

	private Integer reId;
	private String reTitle;
	private String reContent;
	private Integer reGrade;
	
	public Review toEntity() {
		
		return Review.builder().reId(reId).reTitle(reTitle).reContent(reContent).reGrade(reGrade).build();
	}
	
}
