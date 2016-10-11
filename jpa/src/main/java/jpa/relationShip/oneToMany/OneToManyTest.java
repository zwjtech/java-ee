package jpa.relationShip.oneToMany;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class OneToManyTest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;
	
	@Before
	public void init(){
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void testUpdate(){
		Customer2 customer = entityManager.find(Customer2.class, 10);
		
		customer.getOrders().iterator().next().setOrderName("O-XXX-10");
	}
	
	//默认情况下, 若删除 1 的一端, 则会先把关联的 n 的一端的外键置空, 然后进行删除. 
	//可以通过 @OneToMany 的 cascade 属性来修改默认的删除策略. 
	@Test
	public void testOneToManyRemove(){
		Customer2 customer = entityManager.find(Customer2.class, 8);
		entityManager.remove(customer);
	}
	
	//默认对关联的多的一方使用懒加载的加载策略. 即先打印customer,后打印order，没有用左外连接
	//可以使用 @OneToMany 的 fetch 属性来修改默认的加载策略
	@Test
	public void testOneToManyFind(){
		Customer2 customer = entityManager.find(Customer2.class, 9);
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());
	}
	
	//若是双向 1-n 的关联关系, 执行保存时
	//若先保存 n 的一端, 再保存 1 的一端, 默认情况下, 会多出 n 条 UPDATE 语句.
	//若先保存 1 的一端, 则会多出 n 条 UPDATE 语句
	//在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系, 这样会有效的减少 SQL 语句. 
	//注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了. 
	
	//单向 1-n 关联关系执行保存时, 一定会多出 UPDATE 语句.
	//因为 n 的一端在插入时不会同时插入外键列. 
	@Test
	public void testOneToManyPersist(){
		Customer2 customer = new Customer2();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("mm@163.com");
		customer.setLastName("MM");
		
		Order2 order1 = new Order2();
		order1.setOrderName("O-MM-1");
		Order2 order2 = new Order2();
		order2.setOrderName("O-MM-2");
		
		//建立关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		//不管哪保存哪一端,都有3条insert, 2条update
		entityManager.persist(customer);

		entityManager.persist(order1);
		entityManager.persist(order2);
	}

}
