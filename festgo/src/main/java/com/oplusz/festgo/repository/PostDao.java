package com.oplusz.festgo.repository;

import java.util.List;

import com.oplusz.festgo.domain.Post;

public interface PostDao {

	List<Post> selectOrderByIdDesc();

}
