package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.service.FestivalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FestivalCalendarController {
	
    @GetMapping("/fest/cal")
    public String festivalcalendar() {
            log.debug("festivalcalendar()");

            return "post/festivalcal";
    }
    
    private final FestivalService festivalService;

    @Autowired
    public FestivalCalendarController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }
       
    // AJAX 요청: FullCalendar 자동 로딩 시 start, end 파라미터를 전달함.
    // 전달받은 ISO 8601 형식의 문자열에서 날짜 부분만 추출하여 서비스에 전달합니다.
    // 예: "2025-01-26T00:00:00+09:00" → "2025-01-26"
    @ResponseBody
    @GetMapping("/api/festivals")
    public List<FestivalCalendarDto> getFestivalsBetweenDates(
            @RequestParam("start") String start, 
            @RequestParam("end") String end) {
        log.debug("getFestivalsBetweenDates() invoked with start: {}, end: {}", start, end);
        
        // ISO 8601 형식에서 날짜 부분만 추출 (T 이전 부분)
        String startDate = start.contains("T") ? start.split("T")[0] : start;
        String endDate = end.contains("T") ? end.split("T")[0] : end;
        log.debug("Extracted start date: {}, end date: {}", startDate, endDate);
        
        return festivalService.getFestivalsBetweenDates(startDate, endDate);
    }
}