package jpa.relationShip.manyToOneBoth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class ManyToOneBothTest {
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
	
	//若是双向 1-n 的关联关系, 执行保存时
	//若先保存 n 的一端, 再保存 1 的一端, 默认情况下, 会多出4 条 UPDATE 语句.
	//若先保存 1 的一端, 则会多出 2 条 UPDATE 语句

	//在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系（也就是先保存1 的一端）, 这样会有效的减少 SQL 语句.
	//注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了.
	// 	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.REMOVE}, mappedBy="customer3") 使1 的一端放弃维护

	//单向 1-n 关联关系执行保存时, 一定会多出 UPDATE 语句.
	//因为 n 的一端在插入时不会同时插入外键列. 
	@Test
	public void testeManyToOneBothPersist(){
		Customer3 customer = new Customer3();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("mm@163.com");
		customer.setLastName("MM");
		
		Order3 order1 = new Order3();
		order1.setOrderName("O-MM-1");
		Order3 order2 = new Order3();
		order2.setOrderName("O-MM-2");
		
		//建立关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		order1.setCustomer3(customer);
		order2.setCustomer3(customer);
		
		//执行保存操作:先保存1的那一端(建议）
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}

}
