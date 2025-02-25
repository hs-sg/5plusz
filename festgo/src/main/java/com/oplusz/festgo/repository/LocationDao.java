package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Location;

public interface LocationDao {
	List<Location> selectAll();
}