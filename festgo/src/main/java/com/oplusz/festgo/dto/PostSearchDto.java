package com.oplusz.festgo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    private String category;  // 검색 조건 (제목, 내용, 작성자 등)
    private String keyword;   // 검색 키워드
    private int page = 1;         // 현재 페이지
    private Integer pageSize = 5;     // 페이지당 게시글 수
    
    public void setPage(int page) {
        this.page = (page < 1) ? 1 : page;
    }
}
