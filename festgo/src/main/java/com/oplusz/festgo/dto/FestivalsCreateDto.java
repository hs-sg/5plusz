package com.oplusz.festgo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oplusz.festgo.domain.FestivalImages;
import com.oplusz.festgo.domain.Festivals;

public class FestivalsCreateDto {
	// 필드 이름들을 요청 파라미터 이름과 같게 선언 & 기본 생성자 & setter
    private String fName;
    private LocalDateTime fStartDate;
    private LocalDateTime fEndDate;
    private String lcId;
    private String fAddress;
    private String fPhone;
    private String mSponsor;
    private String fFee;
    private Integer theId;
    private String fContents;
    private String fHomepage;
    private String fImageMain;
    private String fPoster;
    private LocalDateTime fCreatedTime;
    private LocalDateTime fModifiedTime;
    private List<String> fiImages;
    
    // Festivals DTO를 Model로 변환해서 리턴하는 메서드
    public Festivals toFestivalsEntity() {
    	return Festivals.builder().fName(fName).fStartDate(fStartDate).fEndDate(fEndDate).lcId(lcId).fAddress(fAddress).fPhone(fPhone)
    			.mSponsor(mSponsor).fFee(fFee).theId(theId).fContents(fContents).fHomepage(fHomepage).fImageMain(fImageMain)
    			.fPoster(fPoster).fCreatedTime(fCreatedTime).fModifiedTime(fModifiedTime).build();
    }
    
    // FestivalImages DTO를 Model로 변환해서 리턴하는 메서드
    public List<FestivalImages> toFestivalImagesEntities(Integer fId) {
        return fiImages.stream()
            .map(image -> FestivalImages.builder()
                .fId(fId)
                .fiImages(image)
                .build())
            .toList();
    }

	
}
