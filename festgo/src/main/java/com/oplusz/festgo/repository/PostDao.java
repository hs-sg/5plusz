package com.oplusz.festgo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oplusz.festgo.domain.Post;
import com.oplusz.festgo.domain.PostAttachment;
import com.oplusz.festgo.dto.PostCreateDto;
import com.oplusz.festgo.dto.PostSearchDto;

public interface PostDao {

    // 게시글 저장 (poId를 자동으로 채움)
    int insert(PostCreateDto dto);

    // 게시글 목록 조회
    List<Post> selectOrderByIdDesc();

    // 첨부파일 저장
    void insertAttachment(@Param("list") List<PostAttachment> attachments);

    // 조회수 증가 메서드
    void increaseViewCount(@Param("poId") Integer poId);

    // 특정 게시글 조회 메서드
    Post selectById(@Param("poId") Integer poId);

    // 게시글 수정
    int update(Post post);

    // 기존 첨부파일 삭제
    void deleteAttachmentsByIds(@Param("ids") List<Integer> attachmentIds);

    // 페이징 처리된 게시글 목록 조회
    List<Post> selectPagedPosts(Map<String, Object> params);

    // 전체 게시글 개수 조회
    int countPosts();

    // 검색과 페이징 처리를 함께 수행
    List<Post> searchWithPaging(Map<String, Object> params);

    // 검색된 게시글 개수 조회
    int countSearchResults(Map<String, Object> params);

	List<Post> selectNotices();
	
	// 게시글 삭제
	void deleteById(@Param("poId") Integer poId);
	
	// 특정 게시글의 모든 첨부파일 삭제
	void deleteAttachmentsByPostId(@Param("poId") Integer poId);
	
	// 검색
	List<Post> search(PostSearchDto dto);
	
	// 특정 게시글의 첨부파일 리스트 조회
	List<String> selectAttachmentsByPostId(@Param("poId") Integer poId);

	List<PostAttachment> selectAttachmentsByPostIdWithObject(@Param("poId") Integer poId);
	
//	희성 작성 시작
	
	List<Post> readByMeUsername(String meUsername);
	List<Post> readVariable(Integer minPostNum, Integer maxPostNum);
	List<Post> readVariableByMeUsername(Integer minPostNum, Integer maxPostNum, String meUsername);
	Integer countAllPosts();
	Integer countPostsByMeUsername(String meUsername);
	
//	희성 작성 끝


}
