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
public class Festival {
    private Integer feId;
    private String feName;
    private LocalDateTime feStartDate;
    private LocalDateTime feEndDate;
    private String lcId;
    private String feAddress;
    private String fePhone;
    private String meSponsor;
    private String feFee;
    private Integer theId;
    private String feContents;
    private String feLat;
    private String feLong;
    private String feHomepage;
    private String feImageMain;
    private String fePoster;
    private LocalDateTime feCreatedTime;
    private LocalDateTime feModifiedTime;
}
