package com.oplusz.festgo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oplusz.festgo.domain.Theme;
import com.oplusz.festgo.repository.ThemeDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeService {

	private final ThemeDao themeDao;
	
	// theme 테이블에 theId theName 추가
	@Transactional
	public Integer create(Theme theme) {
		log.debug("new theme = {}", theme);
		themeDao.insertTheme(theme);
		return theme.getTheId();
	}
	
}
