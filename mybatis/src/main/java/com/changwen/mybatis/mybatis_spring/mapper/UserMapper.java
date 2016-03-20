package com.changwen.mybatis.mybatis_spring.mapper;

import com.changwen.mybatis.bean.MSUser;

import java.util.List;
/*
 * 约定
 */
public interface UserMapper {

	void save(MSUser user);

	void update(MSUser user);

	void delete(int id);

	MSUser findById(int id);

	List<MSUser> findAll();

}
