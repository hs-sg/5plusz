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
public class Comments {
    private Integer cId;
    private String cTitle;
    private String cContent;
    private String cAuthor;
    private LocalDateTime cCreatedTime;
    private LocalDateTime cModifiedTime;
    private Integer cGrade;
    private Integer fId;
}
