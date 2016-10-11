package jpa.relationShip.manyToManyBoth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ManyToManyBothTest {
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

	//对于关联的集合对象, 默认使用懒加载的策略.
	//使用维护关联关系的一方获取, 还是使用不维护关联关系的一方获取, SQL 语句相同. 
	@Test
	public void testManyToManyFind(){
//		Item item = entityManager.find(Item.class, 5);
//		System.out.println(item.getItemName());
//		
//		System.out.println(item.getCategories().size());
		
		Category category = entityManager.find(Category.class, 3);
		System.out.println(category.getCategoryName());
		System.out.println(category.getItems().size());
	}
	
	//多对多的保存
	@Test
	public void testManyToManyPersist(){
		Item i1 = new Item();
		i1.setItemName("i-1");
		Item i2 = new Item();
		i2.setItemName("i-2");
		
		Category c1 = new Category();
		c1.setCategoryName("C-1");
		Category c2 = new Category();
		c2.setCategoryName("C-2");
		
		//设置关联关系
		i1.getCategories().add(c1);
		i1.getCategories().add(c2);
		
		i2.getCategories().add(c1);
		i2.getCategories().add(c2);
		
		c1.getItems().add(i1);
		c1.getItems().add(i2);
		
		c2.getItems().add(i1);
		c2.getItems().add(i2);
		
		//执行保存，8条insert
		entityManager.persist(i1);
		entityManager.persist(i2);
		entityManager.persist(c1);
		entityManager.persist(c2);
	}
}
