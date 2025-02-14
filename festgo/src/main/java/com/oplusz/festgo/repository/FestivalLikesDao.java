package com.oplusz.festgo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FestivalLikesDao {

    void insertLikeByFeIdAndMeId(@Param("feId") int feId, @Param("meId") int meId);

    void deleteLikeByFeIdAndMeId(@Param("feId") int feId, @Param("meId") int meId);

    int countLikesByFeId(@Param("feId") int feId);

    int checkLikeByFeIdAndMeId(@Param("feId") int feId, @Param("meId") int meId);
}
