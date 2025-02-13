package com.oplusz.festgo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.dto.FestivalCreateDto;
import com.oplusz.festgo.dto.FestivalSearchDto;
import com.oplusz.festgo.dto.FestivalWithImagesDto;
import com.oplusz.festgo.repository.FestRequestDao;
import com.oplusz.festgo.repository.FestivalDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FestivalService {

	private final FestivalDao festivalsDao;
	private final FestRequestDao festRequestDao;

	// 축체 등록
	@Transactional
	public FestivalWithImagesDto create(FestivalCreateDto dto) {
		log.debug("create(Festivals = {} )", dto);

		// Festivals 테이블에 데이터 삽입
		Festival festival = dto.toFestivalEntity();
		festivalsDao.insertFestivals(festival);
		log.debug("insertFestivals = {}", festival);
		
		// FEST_REQUEST 테이블에 승인대기 상태(1)로 삽입
        int requestResult = festRequestDao.insertFestRequest(festival.getFeId(), 1);
        if (requestResult != 1) {
            throw new RuntimeException("축제 승인 요청 등록 실패");
        }

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
	
	// 상세보기 서비스
	public Festival read(Integer feId) {
		log.debug("read = {}", feId);
		
		Festival festival = festivalsDao.selectFestivalById(feId);
		
		return festival;
	}
	
	  /**
     * 지정된 기간 (start ~ end, 형식: yyyy-MM-dd) 내의 축제 정보를 조회하여 반환합니다.
     * FullCalendar가 자동으로 start, end 파라미터를 전달할 때 사용합니다.
     */
    public List<FestivalCalendarDto> getFestivalsBetweenDates(String start, String end) {
        log.debug("getFestivalsBetweenDates() invoked with start: {}, end: {}", start, end);
        return festivalsDao.findFestivalsBetween(start, end);
    }
    
    // 축제 검색
    public List<FestivalCalendarDto> read(FestivalSearchDto dto) {
    	log.debug("read(dto={})", dto);
    	if(dto.getKeyword() == null) dto.setKeyword("");
    	
    	return festivalsDao.selectFestivalForSearch(dto);
    }
    
    // 검색한 축제 개수
    public int readForReload(FestivalSearchDto dto) {
    	log.debug("readForReload(dto={})", dto);
    	if(dto.getKeyword() == null) dto.setKeyword("");
    	
    	return festivalsDao.selectFestivalForReload(dto);
    }

    // 홈페이지 메인 화면에 띄울 축제 검색
    public List<Festival> read(List<Integer> ListFeId) {
    	log.debug("read(ListFeId={})", ListFeId);
    	List<Festival> festivals = new ArrayList<Festival>();
    	for (int id : ListFeId) {
    		festivals.add(festivalsDao.selectFestivalById(id));
    	}
    	
    	return festivals;
    }
    
    // 홈페이지 New 축제 화면에 띄울 축제 검색
    public List<Festival> readByCreatedTime() {
    	log.debug("readByCreatedTime()");
    	
    	return festivalsDao.selectFestivalByCreatedTime();
    }



}
