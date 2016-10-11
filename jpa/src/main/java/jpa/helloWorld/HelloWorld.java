package jpa.helloWorld;


import jpa.annotation.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HelloWorld {

	public static void main(String[] args) {
		//1. 创建 EntitymanagerFactory
		//这个名字是persistence.xml配置文件里persistence-unit name="jpa-1"
		String persistenceUnitName = "jpa-1";
		
		
		EntityManagerFactory entityManagerFactory = 
				Persistence.createEntityManagerFactory(persistenceUnitName);
				
		//2. 创建 EntityManager. 类似于 Hibernate 的 SessionFactory
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//3. 开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4. 进行持久化操作
		Customer customer = new Customer();
		customer.setAge(12);
		customer.setEmail("tom@atguigu.com");
		customer.setLastName("Tom");
		
		entityManager.persist(customer);
		
		//5. 提交事务
		transaction.commit();
		
		//6. 关闭 EntityManager
		entityManager.close();
		
		//7. 关闭 EntityManagerFactory
		entityManagerFactory.close();
	}

}
