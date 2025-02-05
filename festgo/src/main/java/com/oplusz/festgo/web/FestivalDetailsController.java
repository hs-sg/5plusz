package com.oplusz.festgo.web;

import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.service.FestivalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FestivalDetailsController {

    @GetMapping("/fest/details")
    public String festivalDetails() {
        log.debug("festivalDetails()");
        // 뷰 리졸버가 "fest/details"를 /WEB-INF/views/fest/details.jsp로 포워딩하도록 설정되어 있다고 가정합니다.
        return "fest/details";
    }
}

