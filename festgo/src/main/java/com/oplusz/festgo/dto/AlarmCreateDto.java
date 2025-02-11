package com.oplusz.festgo.dto;

import com.oplusz.festgo.domain.Alarm;

import lombok.Data;

@Data
public class AlarmCreateDto {
    private Integer alCategory;
    private Integer alSfid;
    private Integer meId;
	
	public Alarm toAlarmEntity() {
		return Alarm.builder().alCategory(alCategory).alSfid(alSfid).meId(meId).build();
	}
}
