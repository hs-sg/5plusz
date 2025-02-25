package com.oplusz.festgo.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    
 //  poModifiedTime을 yyyy-MM-dd 형식으로 변환하는 메서드 추가
    public String getFormattedDate() {
        if (poModifiedTime != null) {
            return poModifiedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";
    }
}

