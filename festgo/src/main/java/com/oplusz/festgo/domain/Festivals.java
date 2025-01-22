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
public class Festivals {
    private Integer fId;
    private String fName;
    private LocalDateTime fStartDate;
    private LocalDateTime fEndDate;
    private String lcId;
    private String fAddress;
    private String fPhone;
    private String mSponsor;
    private String fFee;
    private Integer theId;
    private String fContents;
    private String fLat;
    private String fLong;
    private String fHomepage;
    private String fLikes;
    private String fImageMain;
    private String fPoster;
    private LocalDateTime fCreatedTime;
    private LocalDateTime fModifiedTime;
}
