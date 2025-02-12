package com.oplusz.festgo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Alarm;
import com.oplusz.festgo.domain.FestRequest;
import com.oplusz.festgo.domain.Member;
import com.oplusz.festgo.domain.SponRequest;
import com.oplusz.festgo.dto.AlarmCreateDto;
import com.oplusz.festgo.dto.AlarmNumberDto;
import com.oplusz.festgo.dto.AlarmResponseDto;
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
	
	// [관리자용] 승인대기 축제 개수와 사업자 회원가입 승인 요청 개수를 Map 객체로 리턴
	public Map<String, Integer> read() {
		Map<String, Integer> map = new HashMap<>();
		map.put("frNumbers", frDao.countFestivalByFrApproval(1)); // 승인대기 축제 개수
		map.put("srNumbers", srDao.countSponsorBySrApproval(0)); // 사업자 회원가입 승인 요청 개수
		
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
	
	// [관리자용] 사업자 회원가입요청, 축제 등록을 승인할 때 알람 테이블에 status 수정
	public int update(Integer alCategory, Integer sfid) {
		log.debug("update(alCategory={}, sfid={})", alCategory, sfid);
		
		Integer alSfid = sfid;
		// 사업자 회원가입요청(alCategory: 1)인 경우 파라미터 sfid에 meId 값이 입력됨
		// -> meId로 srId를 불러와서 alSfid 변수에 저장.
		if (alCategory == 1) alSfid = srDao.selectByMeId(sfid).getSrId();
		
		return alarmDao.updateProcess(alCategory, alSfid);		
	}
	
	// 사용자에게 표시가 필요한 알람(al_status: 1)들을 불러와서 종류(계정/축제)별로 데이터를 dto 객체에 입력하고
	// dto 객체들의 리스트를 리턴.
	public List<AlarmResponseDto> read(String meUsername) {
		log.debug("read(meUsername={})", meUsername);
		
		List<AlarmResponseDto> listDtos = new ArrayList<AlarmResponseDto>();
		int meId = memberDao.selectByUsername(meUsername).getMeId();
		List<Alarm> alarms = alarmDao.selectStatus1ByMeId(meId);
		for(Alarm a : alarms) {
			String alarmType = "";
			String message = "";
			int approval = 0;
			switch(a.getAlCategory()) {
			case 1: //-> 사업자 회원가입 요청 관련 알람인 경우
				SponRequest sr = srDao.selectByMeId(meId);	
				approval = sr.getSrApproval(); // 가입 승인 여부를 불러옴.
				if(approval == 1) {
					message = "사업자 회원가입 요청이 승인되었습니다.";
				} else {
					message = "사업자 회원가입 요청이 거절되었습니다.";
				}
				break;
			case 2: //-> 축제 등록 관련 알람인 경우
				FestRequest fr = frDao.selectFestRequestByFeId(a.getAlSfid());
				approval = fr.getFrApproval();
				if(approval == 0) {
					message = "축제 등록이 승인되었습니다.";
				} else {
					message = "축제 등록이 반려되었습니다.";
				}
				break;
			}
			// 알람 출력 dto 객체 생성
			AlarmResponseDto dto = AlarmResponseDto.builder()
						.alId(a.getAlId())
						.alCategory(a.getAlCategory())
						.alarmType(alarmType)
						.alarmMessage(message)
						.alCreatedTime(a.getAlCreatedTime())
						.build();
			listDtos.add(dto);
		}
		
		return listDtos;
	}
	
	// 로그인된 사용자의 알람 개수를 불러옴
	public int read(AlarmNumberDto dto) {
		log.debug("read(dto={})", dto);
		
		int result = 0;
		if(dto.getMrId() == 3) { //-> 관리자 계정인 경우
			result = read().size();
			log.debug("관리자, 알람 개수: {}", result);
		} else { //-> 일반, 사업자 계정인 경우
			int meId = memberDao.selectByUsername(dto.getMeUsername()).getMeId();
			result = alarmDao.countStatus1ByMeId(meId);
			log.debug("meId: {}, 알람 개수: {}", meId, result);
		}
		
		return result;
	}
	
	// 알람의 상태를 확인(al_status: 2)으로 바꿈
	public int update(int alId) {
		log.debug("update(alId={})", alId);
		
		return alarmDao.updateCheck(alId);
	}
}
