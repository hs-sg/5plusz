package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Review;
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

}
