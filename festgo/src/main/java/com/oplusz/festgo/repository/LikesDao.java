package com.oplusz.festgo.repository;

public interface LikesDao {
	
// 희성 작성 시작
	
	Integer deleteLikeByFeIdAndMeId(Integer feId, Integer meId);
	Integer insertLikeByFeIdAndMeId(Integer feId, Integer meId);
	
// 희성 작성 끝
	

}
