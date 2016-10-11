package jpa.api;


import jpa.annotation.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Persistence                   EntityManager#find          EntityManager#merge
 * EntityManagerFactoryEntity    EntityManager#getReference
 *                               EntityManager#persistence
 *                               EntityManager#remove
 *
 * EntityManager 其他方法   EntityTransaction
 */
public class API {

	public static void main(String[] args) {
		
		//1. 创建 EntitymanagerFactory
		//这个名字是persistence.xml配置文件里persistence-unit name="jpa-1"
		String persistenceUnitName = "jpa-1";
		
		//Map<String, Object> properites = new HashMap<String, Object>();
		//该Map的key必须是跟persistence.xml配置文件里property name的值一样
		//properites.put("hibernate.show_sql", false);
		
		//Persistence  类主要是用于获取 EntityManagerFactory 实例
		EntityManagerFactory entityManagerFactory = 
				Persistence.createEntityManagerFactory(persistenceUnitName);
				//Persistence.createEntityManagerFactory(persistenceUnitName, properites); //这个方法用得少
				
		//2. 创建 EntityManager. 其中entityManagerFactory类似于 Hibernate 的 SessionFactory
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//3. 开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4. 进行持久化操作
		Customer customer = new Customer();
		customer.setAge(12);
		customer.setEmail("tom@atguigu.com");
		customer.setLastName("Tom");
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		
		entityManager.persist(customer);
		
		//5. 提交事务
		transaction.commit();
		
		//6. 关闭 EntityManager
		entityManager.close();
		
		//7. 关闭 EntityManagerFactory
		entityManagerFactory.close();
	}

}
