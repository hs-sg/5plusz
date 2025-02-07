package com.oplusz.festgo.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Integer reId;
    private String reTitle;
    private String reContent;
    private String reAuthor;
    private LocalDateTime reCreatedTime;
    private LocalDateTime reModifiedTime;
    private Integer reGrade;
    private Integer feId;
}
