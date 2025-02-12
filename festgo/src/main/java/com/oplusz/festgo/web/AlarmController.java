package com.oplusz.festgo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oplusz.festgo.dto.AlarmNumberDto;
import com.oplusz.festgo.dto.AlarmResponseDto;
import com.oplusz.festgo.service.AlarmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AlarmController {
	private final AlarmService alarmService;
	
	// dto = { Integer mrId, String meUsername }
	// 알람 개수가 리턴됨
	@ResponseBody
	@PostMapping("/api/alarmnumber")
	public ResponseEntity<Integer> read(@RequestBody AlarmNumberDto dto) {
		log.debug("POST read(dto={})", dto);
		int result = alarmService.read(dto); 
		
		return ResponseEntity.ok(result);
	}
	
	// [관리자용] 승인대기 축제 개수와 사업자 회원가입 요청 개수가 리턴됨
	@ResponseBody
	@PostMapping("/api/alarmadmin")
	public ResponseEntity<Map<String, Integer>> read() {
		log.debug("POST read() 관리자용");
		Map<String, Integer> map = alarmService.read();
		// map = { 
		//     "frNumbers": 승인대기 축제 개수, 
		//     "srNumbers": 사업자 회원가입 요청 개수 
		// }
		return ResponseEntity.ok(map);
	}

	// 일반/사업자 회원에게 표시되야하는 알람들이 리스트로 리턴됨
	@ResponseBody
	@PostMapping("/api/alarms")
	public ResponseEntity<List<AlarmResponseDto>> read(@RequestBody int meId) {
		log.debug("POST read() 일반/사업자용");
		List<AlarmResponseDto> listAlarms = alarmService.read(meId);
		
		return ResponseEntity.ok(listAlarms);
	}
	
	// 일반/사업자 회원이 알람을 클릭하면 al_status의 값이 2로 바뀜.
	@PostMapping("/api/alarmcheck")
	public int update(int alId) {
		log.debug("POST update(alId={})", alId);
		
		int result = alarmService.update(alId);
		log.debug("update 결과: {}", result);
		
		return result;
	}
}
