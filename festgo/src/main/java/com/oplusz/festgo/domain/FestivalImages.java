package com.oplusz.festgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalImages {
    private Integer fiId;
    private Integer fId;
    private String fiImages;
}
