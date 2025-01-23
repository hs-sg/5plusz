package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Posts;

public interface PostDao {

	List<Posts> selectOrderByIdDesc();

}
