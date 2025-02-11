package com.oplusz.festgo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.dto.AlarmCreateDto;
import com.oplusz.festgo.repository.AlarmDao;
import com.oplusz.festgo.repository.FestRequestDao;
import com.oplusz.festgo.repository.FestivalDao;
import com.oplusz.festgo.repository.MemberDao;
import com.oplusz.festgo.repository.SponRequestDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmDao alarmDao;
	private final MemberDao memberDao;
	private final SponRequestDao srDao;
	private final FestivalDao festivalDao;
	private final FestRequestDao frDao;
	
	// [관리자용] 승인대기 축제 개수와 사업자 승인요청 개수를 Map 객체로 리턴
	public Map<String, Integer> read() {
		Map<String, Integer> map = new HashMap<>();
		map.put("frNumbers", null/*다오로 불러오는 값*/); // 승인대기 축제 개수
		map.put("srNumbers", null/*다오로 불러오는 값*/); // 사업자 승인요청 개수
		
		return map;
	}
	
	// 사업자 회원가입요청을 보낼 때 알람 테이블에 데이터 추가
	public int create(String meUsername) {
		log.debug("create(meUsername={})", meUsername);
		
		int meId = memberDao.selectByUsername(meUsername).getMeId();
		int srId = srDao.selectByMeId(meId).getSrId();
		int alCategory = 1;
		AlarmCreateDto dto = AlarmCreateDto.builder()
					.alCategory(alCategory).alSfid(srId).meId(meId)
					.build();
		
		return alarmDao.insertRequest(dto.toAlarmEntity());
	}
	
	// [사업자용] 축제를 등록할 때 알람 테이블에 데이터 추가
	public int create(String feName, String meUsername) {
		log.debug("create(feName={})", feName);
		
		int meId = memberDao.selectByUsername(meUsername).getMeId();
		int frId = frDao.selectFrIdByFeId(festivalDao.selectFeIdByName(feName));
		int alCategory = 2;
		AlarmCreateDto dto = AlarmCreateDto.builder()
				.alCategory(alCategory).alSfid(frId).meId(meId)
				.build();
	
		return alarmDao.insertRequest(dto.toAlarmEntity());
	}
	
	// [관리자용] 회원가입요청을 승인할 때 알람 테이블에 status 수정
	public int update(Integer alCategory, Integer sfid) {
		log.debug("update()");
		Integer alSfid = sfid;
		int srId = 0;
		if (alCategory == 1) {
			srId = srDao.selectByMeId(sfid).getSrId();
			alSfid = srId;
		}

		return alarmDao.updateProcess(alCategory, alSfid);		
	}
	
}
