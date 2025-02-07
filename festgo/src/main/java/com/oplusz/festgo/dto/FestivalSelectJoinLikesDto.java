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
public class FestivalSelectJoinLikesDto {
	private Integer feId;
	private String feName;
	private LocalDateTime feStartDate;
	private LocalDateTime feEndDate;
	private String feAddress;
	private String meSponsor;
	private String fePhone;
	private String feImageMain;
	private Integer liId;
	private Integer meId;
}
