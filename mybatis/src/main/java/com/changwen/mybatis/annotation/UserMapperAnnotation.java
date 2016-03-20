package com.changwen.mybatis.annotation;

import java.util.List;

import com.changwen.mybatis.bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 这里的sql语句跟xml里的一样
 */
public interface UserMapperAnnotation {

	@Insert("insert into users(id,name, age) values(#{id},#{name}, #{age})")
	int add(User user);
	
	@Delete("delete from users where id=#{id}")
	int deleteById(int id);
	
	@Update("update users set name=#{name},age=#{age} where id=#{id}")
	int update(User user);
	
	@Select("select * from users where id=#{id}")
	User getById(int id);
	
	@Select("select * from users")
	List<User> getAll();

}
