package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.domain.FestivalImages;
import com.oplusz.festgo.domain.Festivals;
import com.oplusz.festgo.dto.FestivalsCreateDto;
import com.oplusz.festgo.repository.FestivalsDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FestivalsService {
	
	private final FestivalsDao festivalsDao;

    @Transactional
    public int create(FestivalsCreateDto dto) {
        log.debug("create(Festivals = {} )", dto);

        // 1. Festivals 테이블에 데이터 삽입
        Festivals festivals = dto.toFestivalsEntity();
        int result = festivalsDao.insertFestivals(festivals);
        log.debug("insertFestivals = {}", festivals);

        // 2. FestivalImages 테이블에 데이터 삽입
        List<FestivalImages> images = dto.toFestivalImagesEntities(festivals.getFId());
        if (!images.isEmpty()) {
            int imagesResult = festivalsDao.insertFestivalImagesBatch(images);
            if (imagesResult != images.size()) {
                throw new RuntimeException("이미지 삽입 실패");
            }
        }

        return result;
    }
	
}
