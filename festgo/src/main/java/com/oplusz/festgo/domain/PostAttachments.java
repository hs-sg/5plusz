package com.oplusz.festgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAttachments {
    private Integer paId;
    private Integer pId;
    private String paAttachments;
}
