package com.oplusz.festgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SponRequest {
    private Integer srId;
    private Integer meId;
    private Integer srApproval;
    private String srCause;
}
