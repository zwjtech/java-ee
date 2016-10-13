package jpa.relationShip.manyToOne;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class ManyToOneTest {
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
	public void testManyToOneUpdate(){
		Order1 order = entityManager.find(Order1.class, 2);
		order.getCustomer1().setLastName("FFF");
	}
	
	//不能直接删除 1 的一端, 因为有外键约束. 
	@Test
	public void testManyToOneRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		Customer1 customer = entityManager.find(Customer1.class, 7);
		entityManager.remove(customer); //会有异常
	}
	
	//默认情况下, 使用左外连接的方式来获取 n 的一端的对象和其关联的 1 的一端的对象. 
	//可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
	@Test
	public void testManyToOneFind(){
		Order1 order = entityManager.find(Order1.class, 1);
		System.out.println(order.getOrderName());
		
		//sql语句显示：默认是左外链接的方式
		System.out.println(order.getCustomer1().getLastName());
	}

	
	/**
	 * 保存多对一时, 建议先保存 1 的一端, 后保存 n 的一端, 这样不会多出额外的 UPDATE 语句.
	 */
	@Test
	public void testManyToOnePersist(){
		Customer1 customer = new Customer1();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("gg@163.com");
		customer.setLastName("GG");
		
		Order1 order1 = new Order1();
		order1.setOrderName("G-GG-1");
		Order1 order2 = new Order1();
		order2.setOrderName("G-GG-2");
		
		//设置关联关系
		order1.setCustomer1(customer);
		order2.setCustomer1(customer);
		
		//执行保存操作：先保存多的那一端，3条insert语句，多了两条Update语句
/*		entityManager.persist(order1);
		entityManager.persist(order2);
		
		entityManager.persist(customer);*/
		
		//先保存一的那一端，3条insert语句
		entityManager.persist(customer);
		
		entityManager.persist(order1);
		entityManager.persist(order2);
	}

}
