package com.oplusz.festgo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oplusz.festgo.domain.Location;
import com.oplusz.festgo.repository.LocationDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
	private final LocationDao locationDao;
	
	// DB의 location 테이블에 있는 데이터들을 List로 리턴함.
	public List<Location> read() {
		log.debug("read()");
		
		return locationDao.selectAll();
	}
}
