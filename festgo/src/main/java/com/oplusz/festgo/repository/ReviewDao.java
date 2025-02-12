package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Review;

public interface ReviewDao {
	
// 희성 작성 시작
	
	Integer countReviewsByMeUsername(String meUsername);
	Integer countAllReviews();
	List<Review> readReviewVariableByMeUsername(Integer minReviewNum, Integer maxReviewNum, String meUsername);
	List<Review> readReviewVariableByPageNum(Integer minReviewNum, Integer maxReviewNum);
	Integer deleteReviewByReId(Integer reId);
	
// 희성 작성 끝
	
}
//