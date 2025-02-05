package com.oplusz.festgo.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;

import lombok.Data;

@Data
public class PostUpdateDto {
    // 기존 필드 (게시글 정보)
    private Integer poId;
    private String poTitle;
    private String poContent;

    // 새롭게 추가되는 필드 (첨부파일 관련)
    private List<MultipartFile> newAttachments; // 새로 추가할 첨부파일
    private List<Integer> removeAttachmentIds; // 삭제할 첨부파일 ID 목록

    // DTO를 Entity로 변환하는 편의 메서드
    public Post toEntity() {
        return Post.builder()
            .poId(poId)
            .poTitle(poTitle)
            .poContent(poContent)
            .build();
    }

    // 🔹 새로운 첨부파일을 Entity로 변환하는 메서드
    public List<PostAttachment> toPostAttachmentEntities(Integer postId) {
        if (newAttachments == null || newAttachments.isEmpty()) {
            return List.of(); // 첨부파일이 없으면 빈 리스트 반환
        }

        return newAttachments.stream()
            .map(file -> PostAttachment.builder()
                .poId(postId)
                .paAttachments(file.getOriginalFilename()) // 파일명 저장
                .build())
            .toList();
    }
}
