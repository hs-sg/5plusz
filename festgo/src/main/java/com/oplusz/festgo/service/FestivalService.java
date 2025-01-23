package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.dto.FestivalWithImagesDto;
import com.oplusz.festgo.repository.FestivalDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FestivalService {

	private final FestivalDao festivalsDao;

	// 축체 등록
	@Transactional
	public FestivalWithImagesDto create(FestivalCreateDto dto) {
		log.debug("create(Festivals = {} )", dto);

		// Festivals 테이블에 데이터 삽입
		Festival festival = dto.toFestivalEntity();
		festivalsDao.insertFestivals(festival);
		log.debug("insertFestivals = {}", festival);

		// FestivalImages 테이블에 데이터 삽입
		List<FestivalImage> images = dto.toFestivalImagesEntities(festival.getFeId());
		if (!images.isEmpty()) {
			int imagesResult = festivalsDao.insertFestivalImagesBatch(images);
			if (imagesResult != images.size()) {
				throw new RuntimeException("이미지 삽입 실패");
			}
		}

		// 축제 정보와 이미지 생성
		return FestivalWithImagesDto.builder().festival(festival).images(images).build();
	}

}
