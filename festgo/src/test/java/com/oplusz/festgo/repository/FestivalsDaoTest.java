package com.oplusz.festgo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oplusz.festgo.domain.FestivalImages;
import com.oplusz.festgo.domain.Festivals;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/application-context.xml" })
public class FestivalsDaoTest {

	@Autowired
	private FestivalsDao festivalsDao;
	
	// @Test
	public void testInsertFestivals() {
	    // 1. 테스트 데이터를 생성
	    Festivals festivals = Festivals.builder()
	        .fName("헬로")
	        .fStartDate(LocalDateTime.now())
	        .fEndDate(LocalDateTime.now().plusDays(10))
	        .lcId("1")
	        .fAddress("서울특별시")
	        .fPhone("010-1234-5678")
	        .mSponsor("서울")
	        .fFee("무료")
	        .theId(1)
	        .fContents("아이티윌")
	        .fHomepage("https://festival.itwill.com")
	        .fImageMain("main_image.jpg")
	        .fPoster("poster.jpg")
	        .build();

	    int result = festivalsDao.insertFestivals(festivals);

	    log.debug("Insert result: {}", result);
	    log.debug("Generated fId: {}", festivals.getFId());

	    Assertions.assertNotNull(festivals.getFId());
	   Assertions.assertEquals(1, result);
	}

	@Test
	public void testInsertFestivalsImages() {
		List<FestivalImages> images = List.of(
		        FestivalImages.builder().fId(1).fiImages("image1.jpg").build(),
		        FestivalImages.builder().fId(1).fiImages("image2.jpg").build()
		    );
		
		int result = festivalsDao.insertFestivalImagesBatch(images);
		
		Assertions.assertEquals(images.size(), result);
	}
	
	
}
