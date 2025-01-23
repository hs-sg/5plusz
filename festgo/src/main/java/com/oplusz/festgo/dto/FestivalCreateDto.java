package com.oplusz.festgo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oplusz.festgo.domain.FestivalImage;

import lombok.extern.slf4j.Slf4j;

import com.oplusz.festgo.domain.Festival;
import java.util.Map;

@Slf4j
public class FestivalCreateDto {
	
	// lcId를 mapping
    private static final Map<String, Integer> LOCATION_MAP = Map.ofEntries(
            Map.entry("서울", 1),
            Map.entry("경기", 2),
            Map.entry("부산", 3),
            Map.entry("대구", 4),
            Map.entry("인천", 5),
            Map.entry("광주", 6),
            Map.entry("대전", 7),
            Map.entry("울산", 8),
            Map.entry("세종", 9),
            Map.entry("강원", 10),
            Map.entry("충북", 11),
            Map.entry("충남", 12),
            Map.entry("전북", 13),
            Map.entry("전남", 14),
            Map.entry("경북", 15),
            Map.entry("경남", 16),
            Map.entry("제주", 17)
        );
	
	// 필드 이름들을 요청 파라미터 이름과 같게 선언 & 기본 생성자 & setter
	private String feName;
	private LocalDateTime feStartDate;
	private LocalDateTime feEndDate;
	private String feAddress;
	private String fePostcode;
	private String feDetailAddress;
	private String feExtraAddress;
	private String fePhone;
	private String meSponsor;
	private String feFee;
	private Integer theId;
	private String feContents;
	private String feHomepage;
	private String feImageMain;
	private String fePoster;
	private LocalDateTime feCreatedTime;
	private LocalDateTime feModifiedTime;
	private List<String> fiImages;

	// Festivals DTO를 Model로 변환해서 리턴하는 메서드
	public Festival toFestivalEntity() {
		String location = feAddress.substring(0, 2).trim(); // 주소에서 지역 추출
		log.debug("Extracted location: {}", location); // 추출된 지역명 로깅
	    Integer calculatedLcId = LOCATION_MAP.getOrDefault(location, null); // 매핑된 lcId 찾기
	    log.debug("Mapped lcId: {}", calculatedLcId); // 매핑 결과 로깅
	    
	    if (calculatedLcId == null) {
	        throw new IllegalArgumentException("올바르지 않은 지역입니다: " + location);
	    }
	     
	    return Festival.builder()
	            .feName(feName)
	            .feStartDate(feStartDate)
	            .feEndDate(feEndDate)
	            .lcId(calculatedLcId) // 매핑된 lcId 설정
	            .feAddress(feAddress)
	            .fePostcode(fePostcode)
	            .feDetailAddress(feDetailAddress)
	            .feExtraAddress(feExtraAddress)
	            .fePhone(fePhone)
	            .meSponsor(meSponsor)
	            .feFee(feFee)
	            .theId(theId)
	            .feContents(feContents)
	            .feHomepage(feHomepage)
	            .feImageMain(feImageMain)
	            .fePoster(fePoster)
	            .feCreatedTime(feCreatedTime)
	            .feModifiedTime(feModifiedTime)
	            .build();
	}

	// FestivalImages DTO를 Model로 변환해서 리턴하는 메서드
	public List<FestivalImage> toFestivalImagesEntities(Integer feId) {
		return fiImages.stream().map(image -> FestivalImage.builder().feId(feId).fiImages(image).build()).toList();
	}

}
