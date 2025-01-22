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
public class Members {
    private Integer mId;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private String mSponsor;
    private Integer mrId;
    private LocalDateTime mCreatedTime;
    private LocalDateTime mAccessedTime;
}
