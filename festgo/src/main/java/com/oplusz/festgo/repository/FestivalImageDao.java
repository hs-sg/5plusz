package com.oplusz.festgo.repository;

import com.oplusz.festgo.domain.FestivalImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FestivalImageDao {
    
    // 특정 축제의 이미지 목록 조회
    List<FestivalImage> getFestivalImages(@Param("feId") int feId);
    
    // 다중 이미지 저장
    void insertFestivalImage(FestivalImage festivalImage);
}
