package com.oplusz.festgo.dto;

import lombok.Data;

@Data
public class PostCreateDto {
	private String poTitle;
	private String poContent;
	private String poAuthor;
	private String paAttachments;
	private Integer pcId;
}
