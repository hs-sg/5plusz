package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewItemDto {

    private Integer reId;
    private String reAuthor;
    private String reTitle;
    private String reContent;
    private Integer reGrade;
    private java.sql.Timestamp reModifiedTime;

    // 엔터티(Model, 도메인) 객체를 DTO 객체로 변환하는 편의 메서드
    public static ReviewItemDto fromEntity(Review review) {
        if (review != null) {
            return ReviewItemDto.builder()
                .reId(review.getReId())
                .reAuthor(review.getReAuthor())
                .reTitle(review.getReTitle())
                .reContent(review.getReContent())
                .reGrade(review.getReGrade())
                .reModifiedTime(java.sql.Timestamp.valueOf(review.getReModifiedTime()))
                .build();
        } else {
            return null;
        }
    }
    
    
}
