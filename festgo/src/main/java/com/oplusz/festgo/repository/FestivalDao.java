package com.oplusz.festgo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.domain.Festival;

@Mapper
public interface FestivalDao {

    // 새로운 축제 등록 (매퍼 XML의 insertFestivals 사용)
    int insertFestivals(Festival festival);

    // 새로운 축제 등록 시 다중 이미지 처리 (매퍼 XML의 insertFestivalImagesBatch 사용)
    int insertFestivalImagesBatch(@Param("festivalImages") List<?> festivalImages);

    // 특정 기간 내 축제 데이터 조회 (매퍼 XML의 findFestivalsBetween 사용)
    List<FestivalCalendarDto> findFestivalsBetween(@Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);
}
