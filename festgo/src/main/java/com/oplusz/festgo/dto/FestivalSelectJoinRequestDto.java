package com.oplusz.festgo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalSelectJoinRequestDto {
	private Integer feId;
	private Integer frApproval;
	private String frCause;
	private String feName;
	private String fePhone;
	private LocalDateTime feStartDate;
	private LocalDateTime feEndDate;
	private String feAddress;
	private String meSponsor;
	private String feImageMain;
}
