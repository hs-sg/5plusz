package com.oplusz.festgo.dto;

import java.time.LocalDateTime;

import com.oplusz.festgo.domain.Review;

import lombok.Data;

@Data
public class ReviewCreateDto {
	
	private Integer feId;
	private String reTitle;
	private String reContent;
	private String reAuthor;
    private LocalDateTime reCreatedTime;
    private LocalDateTime reModifiedTime;
    private Integer reGrade;
	

	// DTO -> Entity로 변환 메서드
	public Review toEntity() {
		return Review.builder()
			.feId(feId)
			.reTitle(reTitle)
			.reContent(reContent)
			.reAuthor(reAuthor)
			.reCreatedTime(reCreatedTime != null ? reCreatedTime : LocalDateTime.now()) // 기본값 설정
			.reModifiedTime(reModifiedTime != null ? reModifiedTime : LocalDateTime.now()) // 기본값 설정
			.reGrade(reGrade != null ? reGrade : 3) // 기본값 설정 (평점이 없으면 3으로 설정)
			.build(); //
	}

}
