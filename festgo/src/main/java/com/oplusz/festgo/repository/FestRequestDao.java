package com.oplusz.festgo.repository;

import org.apache.ibatis.annotations.Param;

public interface FestRequestDao {
	
//	희성 작성 시작 --------------------------------------------------------------------------------------------------------------------
	
	Integer approveFestivalByFeId(@Param("feId") Integer feId);
	
//	희성 작성 끝 ----------------------------------------------------------------------------------------------------------------------
	
}
