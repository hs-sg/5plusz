package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.dto.FestivalWithImagesDto;
import com.oplusz.festgo.repository.FestivalDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

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
		    int count = 0;
		    // 이미지 리스트를 순회하며 각 이미지를 단건으로 삽입
		    for (FestivalImage image : images) {
		        count += festivalsDao.insertFestivalImage(image);
		    }
		    if (count != images.size()) {
		        throw new RuntimeException("이미지 삽입 실패");
		    }
		}


		// 축제 정보와 이미지 생성
		return FestivalWithImagesDto.builder().festival(festival).images(images).build();
	}
	
	  /**
     * 지정된 기간 (start ~ end, 형식: yyyy-MM-dd) 내의 축제 정보를 조회하여 반환합니다.
     * FullCalendar가 자동으로 start, end 파라미터를 전달할 때 사용합니다.
     */
    public List<FestivalCalendarDto> getFestivalsBetweenDates(String start, String end) {
        log.debug("getFestivalsBetweenDates() invoked with start: {}, end: {}", start, end);
        return festivalsDao.findFestivalsBetween(start, end);
    }



}
