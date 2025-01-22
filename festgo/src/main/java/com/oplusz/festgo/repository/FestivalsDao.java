package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.FestivalImages;
import com.oplusz.festgo.domain.Festivals;

public interface FestivalsDao {
	
	// 새 축제 작성
	int insertFestivals(Festivals festivals);
	int insertFestivalImagesBatch(List<FestivalImages> festivalImages);




}
