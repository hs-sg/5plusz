package com.oplusz.festgo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;

public interface FestivalDao {

	// 새 축제 작성
	int insertFestivals(Festival festivals);
	// 축제 이미지들 추가 @Param은 MyBatis가 XML에서 collection 속성을 참조할 수 있도록 명시.
	int insertFestivalImagesBatch(@Param("festivalImages") List<FestivalImage> festivalImages);
}


