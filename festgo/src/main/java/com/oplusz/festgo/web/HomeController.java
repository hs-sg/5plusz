package com.oplusz.festgo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.domain.Festival;
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
		
		// 홈페이지 메인 비주얼 위치에 출력될 축제들의 feId
		List<Integer> feIds = new ArrayList<Integer>();
		feIds.add(184);
		feIds.add(185);
		// 홈페이지 메인 비주얼 위치에 출력될 축제
		List<Festival> listFestivalForMainVisual = festivalService.read(feIds);
		
		// 콤보박스에 들어갈 데이터(지역, 테마)
		List<Location> listLocation = locationService.read();
		List<Theme> listTheme = themeService.read();
		
		// 추천 키워드란에 들어갈 테마
		List<Theme> listThemesInFestival = themeService.readThemeInFestival();
		
		// 홈페이지 최신 축제 비주얼 위치에 출력될 축제
		List<Festival> listFestivalForNewVisual = festivalService.readByCreatedTime();
		
		model.addAttribute("festivalsForMainVisual1", listFestivalForMainVisual.get(0));
		model.addAttribute("festivalsForMainVisual2", listFestivalForMainVisual.get(1));
		model.addAttribute("locations", listLocation);
		model.addAttribute("themes", listTheme);
		model.addAttribute("themesInFestival", listThemesInFestival);
		model.addAttribute("festivalsForNewVisual", listFestivalForNewVisual);
		
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
	
	// 더보기 버튼 생성을 위해 검색한 축제 개수 확인
	@ResponseBody
	@PostMapping("/api/reloadData")
	public ResponseEntity<Integer> countSearchFestival(@RequestBody FestivalSearchDto dto) {
		log.debug("countSearchFestival(dto={})", dto);
		
		if(dto.getMonth().equals("")) {
			dto.setMonth(null);
		} else {
			String monthFormatted = String.format("%02d", Integer.parseInt(dto.getMonth()));
			dto.setMonth(monthFormatted);
		}
		
		int totalFestivals = festivalService.readForReload(dto);
		int festivals = dto.getStartIndexNum() + 12;
		
		int result = totalFestivals - festivals; //-> 페이지에 출력된 페이지들을 제외한 남은 축제 개수
		
		return ResponseEntity.ok(result);
	}
}
//