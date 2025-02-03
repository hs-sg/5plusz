package com.oplusz.festgo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.oplusz.festgo.domain.Festival;
import com.oplusz.festgo.domain.FestivalImage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class FestivalCreateDto {

    // lcId를 mapping
    public static final Map<String, Integer> LOCATION_MAP = Map.ofEntries(
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

    // 필드 선언
    private String feName;
    private LocalDateTime feStartDate;
    private LocalDateTime feEndDate;
    private Integer lcId;
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

    // Festivals DTO를 Model로 변환
    public Festival toFestivalEntity() {
        // 주소에서 지역 추출
        String location = null;
        if (feAddress != null) {
            location = feAddress.replaceAll("(^[가-힣]{2}).*", "$1");  // 한글 첫 두 글자만 추출
        }

        log.debug("Extracted location: '{}'", location);  // 실제 추출된 값 로그 확인

        // 매핑된 lcId 찾기
        Integer mappedLcId = LOCATION_MAP.get(location);

        log.debug("Mapped lcId: {}", mappedLcId);

        if (mappedLcId == null) {
            throw new IllegalArgumentException("올바르지 않은 지역입니다: " + location);
        }
        
        log.debug("feAddress = ", feAddress);

        return Festival.builder()
                .feName(feName)
                .feStartDate(feStartDate)
                .feEndDate(feEndDate)
                .lcId(mappedLcId)
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
    
    	

    // FestivalImages DTO를 Model로 변환
    public List<FestivalImage> toFestivalImagesEntities(Integer feId) {
        if (fiImages == null || fiImages.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        return fiImages.stream()
                .map(image -> FestivalImage.builder().feId(feId).fiImages(image).build())
                .toList();
    }
    
    @Override
    public String toString() {
        return "FestivalCreateDto{" +
                "feName='" + feName + '\'' +
                ", feStartDate=" + feStartDate +
                ", feEndDate=" + feEndDate +
                ", feAddress='" + feAddress + '\'' +
                ", lcId=" + lcId +
                '}';
    }

}
