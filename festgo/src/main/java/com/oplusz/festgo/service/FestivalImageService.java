package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.repository.FestivalImageDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FestivalImageService {
    private final FestivalImageDao festivalImageDao;

    public FestivalImageService(FestivalImageDao festivalImageDao) {
        this.festivalImageDao = festivalImageDao;
    }

    // 특정 축제의 이미지 가져오기
    public List<FestivalImage> getFestivalImages(int feId) {
        return festivalImageDao.getFestivalImages(feId);
    }

    // 축제 이미지 저장
    public void addFestivalImage(FestivalImage festivalImage) {
    	festivalImageDao.insertFestivalImage(festivalImage);
    }
}
