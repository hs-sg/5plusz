package com.oplusz.festgo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmNumberDto {
	private Integer mrId;
	private String meUsername;
}
