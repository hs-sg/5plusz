package com.oplusz.festgo.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.domain.Location;
import com.oplusz.festgo.domain.Theme;
import com.oplusz.festgo.dto.FestivalCalendarDto;
import com.oplusz.festgo.dto.FestivalSearchDto;
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
	
	@GetMapping("/")
	public String home(Model model) {
		log.debug("home()");
		
		List<Location> listLocation = locationService.read();
		List<Theme> listTheme = themeService.read();
		model.addAttribute("locations", listLocation);
		model.addAttribute("themes", listTheme);
		
		return "home";
	}
	
	// DTO를 만들어서 검색해야할듯..
	@ResponseBody
	@GetMapping("/api/search")
	public List<FestivalCalendarDto> search(FestivalSearchDto dto) {
		return null;
	}
	
}
