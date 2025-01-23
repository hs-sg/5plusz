package com.oplusz.festgo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oplusz.festgo.domain.FestivalImage;
import com.oplusz.festgo.domain.Festival;

public class FestivalCreateDto {
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
    public Festival toFestivalEntity() {
    	return Festival.builder().feName(fName).feStartDate(fStartDate).feEndDate(fEndDate).lcId(lcId).feAddress(fAddress).fePhone(fPhone)
    			.meSponsor(mSponsor).feFee(fFee).theId(theId).feContents(fContents).feHomepage(fHomepage).feImageMain(fImageMain)
    			.fePoster(fPoster).feCreatedTime(fCreatedTime).feModifiedTime(fModifiedTime).build();
    }
    
    // FestivalImages DTO를 Model로 변환해서 리턴하는 메서드
    public List<FestivalImage> toFestivalImagesEntities(Integer feId) {
        return fiImages.stream()
            .map(image -> FestivalImage.builder()
                .feId(feId)
                .fiImages(image)
                .build())
            .toList();
    }

	
}
