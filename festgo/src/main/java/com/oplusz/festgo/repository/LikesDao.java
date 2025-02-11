package com.oplusz.festgo.repository;

public interface LikesDao {
	
// 희성 작성 시작
	
	Integer deleteLikeByFeIdAndMeUsername(Integer feId, Integer meId);
	Integer insertLikeByFeIdAndMeUsername(Integer feId, Integer meId);
	
// 희성 작성 끝
	
}
