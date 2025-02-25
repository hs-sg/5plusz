package com.oplusz.festgo.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithAttachmentsDto {
    private Post post;
    private List<PostAttachment> attachments;
    
 //  poModifiedTime을 yyyy-MM-dd 형식으로 변환하는 메서드 추가
    public String getFormattedDate() {
        if (post != null && post.getPoModifiedTime() != null) {
            return post.getPoModifiedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";
    }
}
