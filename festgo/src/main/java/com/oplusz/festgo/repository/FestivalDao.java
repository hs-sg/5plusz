package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;

public interface FestivalDao {

	// 새 축제 작성
	int insertFestivals(Festival festivals);

	int insertFestivalImagesBatch(List<FestivalImage> festivalImages);
}
