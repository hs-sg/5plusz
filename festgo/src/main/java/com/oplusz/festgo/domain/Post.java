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
public class Post {
    private Integer poId;
    private String poTitle;
    private String poContent;
    private String poAuthor;
    private LocalDateTime poCreatedTime;
    private LocalDateTime poModifiedTime;
    private Integer pcId;
    private Integer poViews;
    private Integer paId;
}

