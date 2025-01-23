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
import com.oplusz.festgo.domain.Festival;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class FestivalDaoTest {

	@Autowired
	private FestivalDao festivalDao;
	
	// @Test
	public void testInsertFestivals() {
	    // 1. 테스트 데이터를 생성
	    Festival festivals = Festival.builder()
	        .feName("헬로")
	        .feStartDate(LocalDateTime.now())
	        .feEndDate(LocalDateTime.now().plusDays(10))
	        .lcId("1")
	        .feAddress("서울특별시")
	        .fePhone("010-1234-5678")
	        .meSponsor("서울")
	        .feFee("무료")
	        .theId(1)
	        .feContents("아이티윌")
	        .feHomepage("https://festival.itwill.com")
	        .feImageMain("main_image.jpg")
	        .fePoster("poster.jpg")
	        .build();

	    int result = festivalDao.insertFestivals(festivals);

	    log.debug("Insert result: {}", result);
	    log.debug("Generated fId: {}", festivals.getFeId());

	    Assertions.assertNotNull(festivals.getFeId());
	   Assertions.assertEquals(1, result);
	}

	@Test
	public void testInsertFestivalsImages() {
		List<FestivalImage> images = List.of(
		        FestivalImage.builder().feId(1).fiImages("image1.jpg").build(),
		        FestivalImage.builder().feId(1).fiImages("image2.jpg").build()
		    );
		
		int result = festivalDao.insertFestivalImagesBatch(images);
		
		Assertions.assertEquals(images.size(), result);
	}
	
	
}
