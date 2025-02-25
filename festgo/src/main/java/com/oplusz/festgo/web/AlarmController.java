package com.oplusz.festgo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	@GetMapping("/api/alarmadmin")
	public ResponseEntity<String> read() throws JsonProcessingException {
		log.debug("POST read() 관리자용");
		Map<String, Integer> map = alarmService.read();
		// map = { 
		//     "frNumbers": 승인대기 축제 개수, 
		//     "srNumbers": 사업자 회원가입 요청 개수 
		// }
		ObjectMapper om = new ObjectMapper(); // Map 객체를 JSON 형식으로 변환하기 위해 jackson-databind를 사용
		String json = om.writeValueAsString(map);
		
		return ResponseEntity.ok(json);
	}

	// 일반/사업자 회원에게 표시되야하는 알람들이 리스트로 리턴됨
	@ResponseBody
	@GetMapping("/api/alarms")
	public ResponseEntity<List<AlarmResponseDto>> read(@RequestParam String meUsername) {
		log.debug("GET read() 일반/사업자용");
		List<AlarmResponseDto> listAlarms = alarmService.read(meUsername);
		
		return ResponseEntity.ok(listAlarms);
	}
	
	// 일반/사업자 회원이 알람을 클릭하면 al_status의 값이 2로 바뀜.
	@GetMapping("/api/alarmcheck")
	public ResponseEntity<Integer> update(@RequestParam int alId) {
		log.debug("GET update(alId={})", alId);
		
		int result = alarmService.update(alId);
		log.debug("update 결과: {}", result);
		
		return ResponseEntity.ok(result);
	}
}
