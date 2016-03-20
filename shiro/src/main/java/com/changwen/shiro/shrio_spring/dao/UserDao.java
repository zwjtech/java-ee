package com.changwen.shiro.shrio_spring.dao;


import com.changwen.shiro.shrio_spring.entity.User;

import java.util.Set;


public interface UserDao {

	/**
	 * 通过用户名查询用户
	 */
	User getByUserName(String userName);

	/**
	 * 通过用户名查询角色信息
	 */
	Set<String> getRoles(String userName);

	/**
	 * 通过用户名查询权限信息
	 */
	Set<String> getPermissions(String userName);
}
