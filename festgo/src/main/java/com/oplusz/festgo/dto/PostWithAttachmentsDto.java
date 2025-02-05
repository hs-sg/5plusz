package com.oplusz.festgo.dto;

import java.util.List;
import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostWithAttachmentsDto {
    private Post post;
    private List<PostAttachment> attachments;
}

