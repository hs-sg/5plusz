package com.oplusz.festgo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlarmResponseDto {
	private Integer alId;
	private Integer alCategory;
	private String alarmType;
	private String alarmMessage;
	private LocalDateTime alCreatedTime;
}
