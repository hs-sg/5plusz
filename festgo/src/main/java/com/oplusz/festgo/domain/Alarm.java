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
public class Alarm {
    private Integer alId;
    private Integer alCategory;
    private Integer alSfid;
    private Integer meId;
    private Integer alStatus;
    private LocalDateTime alCreatedTime;
}