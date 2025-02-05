package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.domain.Location;
import com.oplusz.festgo.domain.Theme;
import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.dto.FestivalSearchDto;
import com.oplusz.festgo.service.FestivalService;
import com.oplusz.festgo.service.LocationService;
import com.oplusz.festgo.service.ThemeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	private final LocationService locationService;
	private final ThemeService themeService;
	private final FestivalService festivalService;
	
	@GetMapping("/")
	public String home(Model model) {
		log.debug("home()");
		
		List<Location> listLocation = locationService.read();
		List<Theme> listTheme = themeService.read();
		model.addAttribute("locations", listLocation);
		model.addAttribute("themes", listTheme);
		
		return "home";
	}
	
	// 홈페이지 축제 검색
	@ResponseBody
	@PostMapping("/api/search")
	public List<FestivalCalendarDto> search(@RequestBody FestivalSearchDto dto) {
		log.debug("search(dto={})", dto);
			
		if(dto.getMonth().equals("")) {
			dto.setMonth(null);
		} else {
			String monthFormatted = String.format("%02d", Integer.parseInt(dto.getMonth()));
			dto.setMonth(monthFormatted);
		}
		
		return festivalService.read(dto);
	}
	
}
