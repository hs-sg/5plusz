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
public class Posts {
	private Integer pId;
	private String pTitle;
	private String pContent;
	private String pAuthor;
	private LocalDateTime pCreatedTime;
	private LocalDateTime pModifiedTime;
	private Integer pcId;
	private Integer pViews;
	private Integer paId;
}
