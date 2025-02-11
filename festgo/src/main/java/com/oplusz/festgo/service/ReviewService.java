package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Review;
import com.oplusz.festgo.dto.ReviewCreateDto;
import com.oplusz.festgo.dto.ReviewItemDto;
import com.oplusz.festgo.dto.ReviewUpdateDto;
import com.oplusz.festgo.repository.ReviewDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewDao reviewDao;

//	희성 작성 시작
	
	// 마이페이지 상에 관리자가 모든글 원하는 갯수대로 가져오기
	public List<Review> readReviewVariableByPageNum(Integer reviewNumberInList, Integer pageNum) {
		log.debug("readReviewVariableByPageNum(reviewNumberInList={}, pageNum={})", reviewNumberInList, pageNum);
		Integer minPostNum = 1 + (reviewNumberInList * (pageNum - 1));
		Integer maxPostNum = pageNum * reviewNumberInList;
		
		List<Review> list = reviewDao.readReviewVariableByPageNum(minPostNum, maxPostNum);
		log.debug("# of search result = {}", list.size());
		
		return list;
	}
	
	// 마이페이지 상에 유저가 작성한 리뷰 원하는 갯수대로 가져오기
	public List<Review> readReviewVariableByPageNumAndUsername(Integer reviewNumberInList, Integer pageNum, String username) {
		log.debug("readVariableByPageNumAndUsername(reviewNumberInList={}, pageNum={}, username={})", reviewNumberInList, pageNum, username);
		Integer minReviewNum = 1 + (reviewNumberInList * (pageNum - 1));
		Integer maxReviewNum = pageNum * reviewNumberInList;
		
		List<Review> list = reviewDao.readReviewVariableByMeUsername(minReviewNum, maxReviewNum, username);
		log.debug("# of search result = {}", list.size());
		
		return list;
	}
	
	// 전체 리뷰 갯수 가져오기
	public Integer getCountAllReviews() {
		log.debug("getCountAllReviews()");
		
		Integer countReviews = reviewDao.countAllReviews();
		log.debug("countPosts result = {}", countReviews);
		
		return countReviews;
	}
	
	// 아이디로 리뷰 갯수 가져오기
	public Integer getCountAllReviewsByUsername(String username) {
		log.debug("getCountAllReviewsByUsername(username={})", username);
		
		Integer countReviews = reviewDao.countReviewsByMeUsername(username);
		log.debug("countPosts result = {}", countReviews);
		
		return countReviews;
	}
	
	// 마이페이지에서 리뷰 삭제하기
	public Integer delete(Integer reId) {
		log.debug("deleteReviewByReId(reId={})", reId);
		
		Integer deleteReviewResult = reviewDao.deleteReviewByReId(reId);
		log.debug("deleteReviewResult = {}", deleteReviewResult);
		
		return deleteReviewResult;
	}
	
//	희성 작성 끝
	
	// 해당 아이디의 댓글 (1개)를 검색하는 서비스
	public ReviewItemDto readById(Integer reId) {
		log.debug("readById = {}", reId);
		
		// 영속성 계층의 메서드를 호출해서 select 쿼리를 실행
		Review review = reviewDao.selectById(reId);
		
		return ReviewItemDto.fromEntity(review);
	}
	
	// 특정 포스트에 달려 있는 모든 댓글을 검색하는 서비스
	public List<ReviewItemDto> readByFestivalId(Integer feId) {
		log.debug("readByPostId = {}", feId);
		
		List<Review> list = reviewDao.selectByFestivalId(feId);

		return list.stream().map(ReviewItemDto::fromEntity).toList(); 
		
	}
	
	// 특정 포스트에 댓글을 추가하는 서비스
	public int create(ReviewCreateDto dto) {
		log.debug("create(dto={})", dto);
		
		int result = reviewDao.insertReview(dto.toEntity());
		
		return result;
	}
	
	// 아이디가 일치하는 댓글을 삭제하는 서비스
	public int deleteByReId(Integer reId) {
		log.debug("delete(id={})", reId);
		
		return reviewDao.deleteByReId(reId);
	}
	
	// 댓글 내용을 수정하는 서비스
	public int update(ReviewUpdateDto dto) {
		log.debug("update(dto={})", dto);
		
		int result = reviewDao.updateReview(dto.toEntity());
		
		return result;
	}
	
	

}
