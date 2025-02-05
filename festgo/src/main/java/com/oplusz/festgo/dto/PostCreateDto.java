package com.oplusz.festgo.dto;

import java.util.List;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;

import lombok.Data;

@Data
public class PostCreateDto {
	private String poTitle;
	private String poContent;
	private String poAuthor;
	private Integer pcId;
	private Integer poId;
	private List<String> paAttachments;
	
	public Post toPostEntity() {
		return Post.builder().poTitle(poTitle).poContent(poContent).poAuthor(poAuthor).pcId(pcId).build();	
	}
	
	public List<PostAttachment> toPostattachmentsEntities(Integer poId){
		 if (paAttachments == null || paAttachments.isEmpty()) {
	            return List.of(); // 빈 리스트 반환
	        }

	        return paAttachments.stream()
	                .map(image -> PostAttachment.builder().poId(poId).paAttachments(image).build())
	                .toList();
	    }
}
