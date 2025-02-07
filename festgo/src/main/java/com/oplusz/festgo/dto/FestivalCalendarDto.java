package com.oplusz.festgo.dto;

import java.time.format.DateTimeFormatter;

import com.oplusz.festgo.domain.Festival;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalCalendarDto {
    private Integer feId;             // 축제 ID
    private String feName;            // 축제 이름
    private String feStartDate;       // 축제 시작 날짜 (yyyy-MM-dd)
    private String feEndDate;         // 축제 종료 날짜 (yyyy-MM-dd)
    private String feImageMain;       // 축제 이미지
    private String feAddress;   // 주소

    // 날짜 포맷터 상수
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 도메인 객체(Festival)에서 DTO로 변환하는 편의 메서드  
     * (MyBatis 매퍼 XML에서 resultType으로 사용하여 자동 매핑 가능)
     */
    public static FestivalCalendarDto from(Festival festival) {
        return FestivalCalendarDto.builder()
                .feId(festival.getFeId())
                .feName(festival.getFeName())
                .feStartDate(festival.getFeStartDate() != null ? festival.getFeStartDate().format(FORMATTER) : null)
                .feEndDate(festival.getFeEndDate() != null ? festival.getFeEndDate().format(FORMATTER) : null)
                .feImageMain(festival.getFeImageMain())
                .feAddress(festival.getFeAddress())
                .build();
    }
}
