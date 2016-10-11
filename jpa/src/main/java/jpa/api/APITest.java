package jpa.api;


import jpa.annotation.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class APITest {
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
	/**
	 * 让缓存中的状态跟数据表中的记录保持一致（与flush相反),会有两个 select
	 * 同 hibernate 中 Session 的 refresh 方法. 
	 */
	@Test
	public void testRefresh(){
		Customer customer = entityManager.find(Customer.class, 1);
		customer = entityManager.find(Customer.class, 1);
		
		entityManager.refresh(customer);
	}
	
	/**
	 * 运行到该方法并在提交事务前 ，会强制发送update语句，（数据库的数据没有改变，因为没提交事务）
	 * 使数据表中的记录跟缓存中对象状态保持一致,刷新缓存
	 * 
	 * 同 hibernate 中 Session 的 flush 方法. 
	 */
	@Test
	public void testFlush(){
		Customer customer = entityManager.find(Customer.class, 1);
		System.out.println(customer);
		
		customer.setLastName("AA");
		
		entityManager.flush();
	}
	
	//情况4: 若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存中有对应的对象
	//2. JPA 会把游离对象的属性复制到查询到EntityManager 缓存中的对象中.
	//3. EntityManager 缓存中的对象执行 UPDATE. 
	@Test
	public void testMerge4(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");
		
		customer.setId(4);
		Customer customer2 = entityManager.find(Customer.class, 4);
		
		entityManager.merge(customer);
		
		System.out.println(customer == customer2); //false
	}
	
	
	//情况3: 若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存中没有该对象
	//2. 若在数据库中也（有！！！）对应的记录
	//3. JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
	//4. 对查询到的对象执行 update 操作. 
	@Test
	public void testMerge3(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("ee@163.com");
		customer.setLastName("EE");//setLastName方法中变化EE -- DD --EE
		
		customer.setId(4);
		
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println(customer == customer2); //false
	}
	
	//情况2.若传入的是一个游离对象, 即传入的对象有 OID. 
	//1. 若在 EntityManager 缓存中没有该对象
	//2. 若在数据库中也（没有！！！）对应的记录
	//3. JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
	//4. 对新创建的对象执行 insert 操作. 
	@Test
	public void testMerge2(){
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");
		
		customer.setId(100);
		
		Customer customer2 = entityManager.merge(customer);
		
		//customer没有被持久化，因为如果是持久化，则id不可能为100
		System.out.println("customer#id:" + customer.getId()); //100
		System.out.println("customer2#id:" + customer2.getId()); //4
	}
	
	/**
	 * 总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
	 */
	//情况1. 若传入的是一个临时对象,没有id
	//会创建一个新的对象, 把临时对象的属性复制到新的对象中, 然后对新的对象执行持久化操作. 
	//所以新的对象中有 id, 但以前的临时对象中没有 id.
	@Test
	public void testMerge1(){
		Customer customer = new Customer();
		//注意不要设置id,不然会出现异常
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("cc@163.com");
		customer.setLastName("CC");
		
		//merge返回一个Customer对象
		Customer customer2 = entityManager.merge(customer);
		
		System.out.println("customer#id:" + customer.getId()); //null
		System.out.println("customer2#id:" + customer2.getId()); //3
	}
	
	/************************************************************/
	/**
	 * 类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
	 * 但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
	 */
	@Test
	public void testRemove(){
//		Customer customer = new Customer();
//		customer.setId(2);
		
		Customer customer = entityManager.find(Customer.class, 2);
		entityManager.remove(customer);
	}
	
	/**
	 * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态. 
	 * 	和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常.而hibernate可以 
	 */
	@Test
	public void testPersistence(){
		Customer customer = new Customer();
		customer.setAge(15);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");
		//customer.setId(100); //若这里设置 id, 则不能执行 insert 操作, 而会抛出异常. 
		
		entityManager.persist(customer);
		System.out.println(customer.getId());
	}
	
	/**
	 * #getReference类似于 hibernate 中 Session 的 load 方法，会出现懒加载异常
	 * 
	 * 这里与下面的#find输出有点不同: 是先在控制台上打印代理对象，再细线!!，再打sql语句(因为需要才打印的)!!，再打结果
	 */
	@Test
	public void testGetReference(){
		Customer customer = entityManager.getReference(Customer.class, 1);
		System.out.println(customer.getClass().getName()); //com.changwen.jpa.api.Customer_$$_javassit
		
		System.out.println("-------------------------------------");
//		transaction.commit();
//		entityManager.close();
		System.out.println(customer);
	}
	
	/**
	 * #find 类似于 hibernate 中 Session 的 get 方法.
	 */
	@Test
	public void testFind() {
		//这里是先在控制台上打印sql语句，再打细线，再打结果
		Customer customer = entityManager.find(Customer.class, 1);
		System.out.println("-------------------------------------");
		System.out.println(customer);
	}
}
