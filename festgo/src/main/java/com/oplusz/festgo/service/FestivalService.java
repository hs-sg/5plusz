package com.oplusz.festgo.service;

import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.repository.FestivalDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalDao festivalDao;
    
    /**
     * 지정된 기간 (start ~ end, 형식: yyyy-MM-dd) 내의 축제 정보를 조회하여 반환합니다.
     * FullCalendar가 자동으로 start, end 파라미터를 전달할 때 사용합니다.
     */
    public List<FestivalCalendarDto> getFestivalsBetweenDates(String start, String end) {
        log.debug("getFestivalsBetweenDates() invoked with start: {}, end: {}", start, end);
        return festivalDao.findFestivalsBetween(start, end);
    }
}
