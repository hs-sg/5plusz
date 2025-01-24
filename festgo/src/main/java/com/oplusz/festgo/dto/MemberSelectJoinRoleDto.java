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
public class MemberSelectJoinRoleDto {
	private Integer meId;
	private String meUsername;
	private String meEmail;
	private String meSponsor;
	private LocalDateTime meCreatedTime;
	private Integer mrId;
	private String mrRoles;
}
