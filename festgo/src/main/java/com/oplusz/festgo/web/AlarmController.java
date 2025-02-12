package com.oplusz.festgo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.service.AlarmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AlarmController {
	private final AlarmService alarmService;
	
	// [관리자용] 승인대기 축제 개수와 사업자 승인요청 개수 알람
	@ResponseBody
	@PostMapping("/api/alarmadmin")
	public ResponseEntity<Map<String, Integer>> read() {
		Map<String, Integer> map = alarmService.read();
		
		return ResponseEntity.ok(map);
	}
	
	
}