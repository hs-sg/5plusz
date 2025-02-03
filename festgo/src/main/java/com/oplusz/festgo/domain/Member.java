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
public class Member {
    private Integer meId;
    private String meUsername;
    private String mePassword;
    private String meEmail;
    private String meSponsor;
    private Integer mrId;
    private LocalDateTime meCreatedTime;
    private LocalDateTime meAccessedTime;
}

