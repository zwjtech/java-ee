package com.changwen.mybatis.mybatis_spring;


import java.util.Date;

import com.changwen.mybatis.bean.MSUser;
import com.changwen.mybatis.mybatis_spring.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //w使用Spring的测试构架
@ContextConfiguration("/mybatis-spring/beans.xml") //加载Spring的配置文件beans.xml
public class SMTest {
	@Autowired
	private UserMapper mapper;
	
	
	@Test
	public void testAdd() {
		MSUser user = new MSUser(1, "tom", new Date(), 1234);
		mapper.save(user);
		
		int id = user.getId();
		System.out.println(id);
	}
}

