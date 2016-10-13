/*
 */
package com.changwen.tool.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class BeanFactory {

	/**
	 *
	 */
	public BeanFactory() {
	}

	static ApplicationContext context;
	static {
		try {
			context = new ClassPathXmlApplicationContext(
					new String[] { "sys-root.xml" });
		} catch (Throwable t) {
			if (t instanceof Error) {
				System.out.println("BeanFactory初始化出现错误，原因是：");
				t.printStackTrace();
				throw (Error) t;
			} else {
				System.out.println("BeanFactory初始化出现错误，原因是：");
				t.printStackTrace();
			}
		}
		System.out.println("BeanFactory启动成功");
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

}
