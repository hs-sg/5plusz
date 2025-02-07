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
        
        return "fest/details";
    }
}

