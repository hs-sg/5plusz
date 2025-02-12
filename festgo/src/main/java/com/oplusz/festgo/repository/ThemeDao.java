package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Theme;

public interface ThemeDao {
	    void insertTheme(Theme theme);
	    List<Theme> findAllThemes();
	    
	    // 현재 등록 되어있는 축제들의 theme 불러오기
	    List<Theme> selectThemeInFestival();
}
//