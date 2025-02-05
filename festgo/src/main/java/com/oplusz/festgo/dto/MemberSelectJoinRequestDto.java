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
public class MemberSelectJoinRequestDto {
	private Integer meId;
	private String meUsername;
	private String meSponsor;
	private String meEmail;
	private LocalDateTime meCreatedTime;
}
