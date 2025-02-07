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
public class Comment {
    private Integer coId;
    private String coTitle;
    private String coContent;
    private String coAuthor;
    private LocalDateTime coCreatedTime;
    private LocalDateTime coModifiedTime;
    private Integer coGrade;
    private Integer feId;
}
