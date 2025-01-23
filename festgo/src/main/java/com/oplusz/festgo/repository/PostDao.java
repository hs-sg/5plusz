package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.dto.PostCreateDto;

public interface PostDao {
	List<Post> selectOrderByIdDesc(); // 게시글 목록 조회

	int insert(PostCreateDto dto); // 게시글 생성 쿼리 실행

}
