package com.oplusz.festgo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	// theme 테이블에 입력된 데이터를 List 객체로 리턴
	public List<Theme> read() {
		log.debug("read()");
		
		return themeDao.findAllThemes();
	}
	
	// festival 테이블에서 사용중인 theme들을 List로 리턴.
	public List<Theme> readThemeInFestival() {
		log.debug("readThemeInFestival()");
		List<Theme> themes = themeDao.selectThemeInFestival();
		List<Theme> themesRandom = new ArrayList();
		int listSize = themes.size();
		Random random = new Random();
		if (listSize <= 3) { // 사용중인 theme이 3개 이하인 경우 리스트를 그대로 리턴.
			return themes;
		} else { // 사용중인 theme이 4개 이상일 경우 랜덤으로 3개를 추출해 리스트 생성 후 리턴.
			for(int i = 0; i < 3; i++) {
				int index = random.nextInt(0, themes.size());
				Theme removed = themes.get(index);
				themes.remove(index);
				themesRandom.add(removed);
			}
		}
		
		return themesRandom;
	}
}
