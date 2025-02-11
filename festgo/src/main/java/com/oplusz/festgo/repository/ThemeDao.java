package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Theme;

public interface ThemeDao {
	    void insertTheme(Theme theme);
	    List<Theme> findAllThemes();
}
