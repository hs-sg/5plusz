package com.oplusz.festgo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oplusz.festgo.domain.FestivalImage;

import com.oplusz.festgo.dto.FestivalCreateDto;

import com.oplusz.festgo.dto.FestivalSelectJoinLikesDto;
import com.oplusz.festgo.dto.FestivalSelectJoinRequestDto;

import com.oplusz.festgo.domain.Festival;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class FestivalDaoTest {

	@Autowired
	private FestivalDao festivalDao;
	
	   @Test
	    public void testInsertFestivalWithImages() {
	        // 1. 축제 데이터 준비
		   Festival festival = Festival.builder()
			        .feName("LC아이디 확인축제")
			        .feStartDate(LocalDateTime.now())
			        .feEndDate(LocalDateTime.now().plusDays(10))
			        .fePostcode("08868")
			        .feAddress("세종") 
			        .lcId(FestivalCreateDto.LOCATION_MAP.get("세종"))  // 직접 lcId 설정
			        .feDetailAddress("2층")
			        .feExtraAddress("(역삼동)")
			        .fePhone("010-1234-5678")
			        .meSponsor("최하신팀장")
			        .feFee("무료")
			        .theId(1)
			        .feContents("축제 상세 내용")
			        .feHomepage("https://festival.itwill.com")
			        .feImageMain("main_image.jpg")
			        .fePoster("poster.jpg")
			        .build();

	        
	        Assertions.assertNotNull(festival.getLcId(), "lcId 값이 null입니다.");
	        log.debug("lcId: {}", festival.getLcId());

	        // 2. 축제 데이터 삽입 및 검증
	        int result = festivalDao.insertFestivals(festival);
	        log.debug("Insert Festival Result: {}", result);
	        log.debug("Generated feId: {}", festival.getFeId());

	        Assertions.assertNotNull(festival.getFeId());
	        Assertions.assertEquals(1, result);
	        
	        Integer feId = festival.getFeId(); // 삽입된 축제의 feId 가져오기


	        // 3. 이미지 데이터 준비
	        List<FestivalImage> images = List.of(
	            FestivalImage.builder().feId(feId).fiImages("image1.jpg").build(),
	            FestivalImage.builder().feId(feId).fiImages("image2.jpg").build()
	        );

	        // 4. 이미지 데이터 삽입 및 검증
	        for (FestivalImage image : images) {
	            int imageResult = festivalDao.insertFestivalImagesBatch(List.of(image));
	            log.debug("Inserted Image: {}", image);
	            Assertions.assertEquals(1, imageResult);
	        }
	    }

	
//	희성 작성 시작 -------------------------------------------------------------------------------------------------
	// @Test
	public void testSelectFestivalAll() {
		List<Festival> list = festivalDao.selectFestivalAll();
		for(Festival f : list) {
			log.debug(f.toString());
		}
		
		Assertions.assertNotNull(list);
	}

	
	//@Test
	public void testSelectFestivalJoinLikesByMemberId() {
		List<FestivalSelectJoinLikesDto> list = festivalDao.selectFestivalJoinLikesByMemberId(23);
		for(FestivalSelectJoinLikesDto f : list) {
			log.debug(f.toString());
		}
		
		Assertions.assertNotNull(list);
	}
	
	// @Test
	public void testSelectFestivalJoinRequestAll() {
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestAll();
		for(FestivalSelectJoinRequestDto f : list) {
			log.debug(f.toString());
		}
		
		Assertions.assertNotNull(list);
	}
	
	// @Test
	public void selectFestivalJoinRequestBySponsor() {
		List<FestivalSelectJoinRequestDto> list = festivalDao.selectFestivalJoinRequestBySponsor("서울");
		for(FestivalSelectJoinRequestDto f : list) {
			log.debug(f.toString());
		}
		
		Assertions.assertNotNull(list);
	}
	
//	희성 작성 끝 -------------------------------------------------------------------------------------------------
}
