package com.oplusz.festgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestRequest {
    private Integer frId;
    private Integer feId;
    private Integer frApproval;
    private String frCause;
}
