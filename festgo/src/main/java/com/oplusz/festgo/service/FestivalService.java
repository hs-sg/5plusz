package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.repository.FestivalDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FestivalService {
	
	private final FestivalDao festivalsDao;

    @Transactional
    public int create(FestivalCreateDto dto) {
        log.debug("create(Festivals = {} )", dto);

        // 1. Festivals 테이블에 데이터 삽입
        Festival festivals = dto.toFestivalEntity();
        int result = festivalsDao.insertFestivals(festivals);
        log.debug("insertFestivals = {}", festivals);

        // 2. FestivalImages 테이블에 데이터 삽입
        List<FestivalImage> images = dto.toFestivalImagesEntities(festivals.getFeId());
        if (!images.isEmpty()) {
            int imagesResult = festivalsDao.insertFestivalImagesBatch(images);
            if (imagesResult != images.size()) {
                throw new RuntimeException("이미지 삽입 실패");
            }
        }

        return result;
    }
	
}
