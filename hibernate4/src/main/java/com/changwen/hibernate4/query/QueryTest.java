package com.changwen.hibernate4.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.changwen.hibernate4.query.dao.DepartmentDao;
import com.changwen.hibernate4.query.entities.Department;
import com.changwen.hibernate4.query.entities.Employee;

import com.changwen.hibernate4.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class QueryTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				                            .buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

	/**
	 * Hibernate 提供了以下几种检索对象的方式
	 * 1、导航对象图检索方式:  根据已经加载的对象导航到其他对象
	 * 2、OID 检索方式:  按照对象的 OID 来检索对象
	 * 3、HQL 检索方式: 使用面向对象的 HQL 查询语言
	 * 4、QBC 检索方式: 使用 QBC(Query By Criteria) API 来检索对象. 这种 API 封装了基于字符串形式的查询语句, 提供了更加面向对象的查询接口.
	 * 5、本地 SQL 检索方式: 使用本地数据库的 SQL 查询语句
	 */

	@Test
	public void testHQLUpdate(){
		String hql = "DELETE FROM Department d WHERE d.id = :id";
		
		session.createQuery(hql).setInteger("id", 280)
		                        .executeUpdate();
	}

	/**
	 * 本地SQL查询来完善HQL不能涵盖所有的查询特性
	 */
	@Test
	public void testNativeSQL(){
		String sql = "INSERT INTO gg_department VALUES(?, ?)";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(1, 280)
		     .setString(2, "ATGUIGU")
		     .executeUpdate();
	}

	/**
	 * QBC 查询就是通过使用 Hibernate 提供的 Query By Criteria API 来查询对象，
	 * 这种 API 封装了 SQL 语句的动态拼装，对查询提供了更加面向对象的功能接口本地SQL查询来完善HQL不能涵盖所有的查询特性
	 */
	@Test
	public void testQBC4(){
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. 添加排序
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		
		//2. 添加翻页方法
		int pageSize = 5;
		int pageNo = 3;
		criteria.setFirstResult((pageNo - 1) * pageSize)
		        .setMaxResults(pageSize)
		        .list();
	}
	
	@Test
	public void testQBC3(){
		Criteria criteria = session.createCriteria(Employee.class);
		
		//统计查询: 使用 Projection 来表示: 可以由 Projections 的静态方法得到
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult()); 
	}
	
	@Test
	public void testQBC2(){
		Criteria criteria = session.createCriteria(Employee.class);
		
		//1. AND: 使用 Conjunction 表示
		//Conjunction 本身就是一个 Criterion 对象
		//且其中还可以添加 Criterion 对象
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("name", "a", MatchMode.ANYWHERE));
		Department dept = new Department();
		dept.setId(80);
		conjunction.add(Restrictions.eq("dept", dept));
		System.out.println(conjunction); 
		
		//2. OR
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 6000F));
		disjunction.add(Restrictions.isNull("email"));
		
		criteria.add(disjunction);
		criteria.add(conjunction);
		
		criteria.list();
	}
	
	@Test
	public void testQBC(){
		//1. 创建一个 Criteria 对象
		Criteria criteria = session.createCriteria(Employee.class);
		
		//2. 添加查询条件: 在 QBC 中查询条件使用 Criterion 来表示
		//Criterion 可以通过 Restrictions 的静态方法得到
		criteria.add(Restrictions.eq("email", "SKUMAR"));
		criteria.add(Restrictions.gt("salary", 5000F));
		
		//3. 执行查询
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee); 
	}
	/*********************************HQL**********************************/
	/**
	 * 内连接: INNER JOIN 关键字表示内连接, 也可以省略 INNER 关键字
	 *
	 *   list() 方法的集合中存放的每个元素对应查询结果的一条记录,
	 * 每个元素都是对象数组类型如果希望 list() 方法的返回的集合仅包含 Department  对象,
	 * 可以在 HQL 查询语句中使用 SELECT 关键字
	 */
	@Test
	public void testLeftJoinFetch2(){
		String hql = "SELECT e FROM Employee e INNER JOIN e.dept";
		Query query = session.createQuery(hql);
		
		List<Employee> emps = query.list();
		System.out.println(emps.size()); 
		
		for(Employee emp: emps){
			System.out.println(emp.getName() + ", " + emp.getDept().getName());
		}
	}

	/**
	 * 迫切内连接:INNER JOIN FETCH 关键字表示迫切内连接, 也可以省略 INNER 关键字
	 *
	 * list() 方法返回的集合中存放 Department 对象的引用,
	 * 每个 Department 对象的 Employee 集合都被初始化, 存放所有关联的 Employee 对象
	 */
	/**
	 * 左外连接: LEFT JOIN 关键字表示左外连接查询.
	 *
	 * list() 方法返回的集合中存放的是对象数组类型根据配置文件来决定 Employee 集合的检索策略.
	 * 如果希望 list() 方法返回的集合中仅包含 Department 对象, 可以在HQL 查询语句中使用 SELECT 关键字
	 */
	@Test
	public void testLeftJoin(){
		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		System.out.println(depts.size());
		
		for(Department dept: depts){
			System.out.println(dept.getName() + ", " + dept.getEmps().size()); 
		}
		
//		List<Object []> result = query.list(); 
//		result = new ArrayList<>(new LinkedHashSet<>(result));
//		System.out.println(result); 
//		
//		for(Object [] objs: result){
//			System.out.println(Arrays.asList(objs));
//		}
	}

	/**
	 * 迫切左外连接: LEFT JOIN FETCH 关键字表示迫切左外连接检索策略.
	 *
	 * list() 方法返回的集合中存放实体对象的引用, 每个 Department 对象关联的 Employee  集合都被初始化,
	 * 		存放所有关联的 Employee 的实体对象.查询结果中可能会包含重复元素, 可以通过一个 HashSet 来过滤重复元素
	 */
	@Test
	public void testLeftJoinFetch(){
//		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.emps";
		String hql = "FROM Department d INNER JOIN FETCH d.emps";
		Query query = session.createQuery(hql);
		
		List<Department> depts = query.list();
		depts = new ArrayList<Department>(new LinkedHashSet(depts));
		System.out.println(depts.size()); 
		
		for(Department dept: depts){
			System.out.println(dept.getName() + "-" + dept.getEmps().size());
		}
	}

	/**
	 * 报表查询:
	 * 报表查询用于对数据分组和统计, 与 SQL 一样, HQL 利用 GROUP BY 关键字对数据分组, 用 HAVING 关键字对分组数据设定约束条件.
	 * 在 HQL 查询语句中可以调用以下聚集函数count()、min()、max()、sum()、avg()
	 */
	@Test
	public void testGroupBy(){
		String hql = "SELECT min(e.salary), max(e.salary) "
				+ "FROM Employee e "
				+ "GROUP BY e.dept "
				+ "HAVING min(salary) > :minSal";
		
		Query query = session.createQuery(hql)
				             .setFloat("minSal", 8000);
		
		List<Object []> result = query.list();
		for(Object [] objs: result){
			System.out.println(Arrays.asList(objs));
		}
	}
	
	@Test
	public void testFieldQuery2(){
		String hql = "SELECT new Employee(e.email, e.salary, e.dept) "
				+ "FROM Employee e "
				+ "WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Employee> result = query.setEntity("dept", dept)
				                     .list();
		
		for(Employee emp: result){
			System.out.println(emp.getId() + ", " + emp.getEmail() 
					+ ", " + emp.getSalary() + ", " + emp.getDept());
		}
	}

	/**
	 * 投影查询:
	 * 		查询结果仅包含实体的部分属性. 通过 SELECT 关键字实现.
	 * Query 的 list() 方法返回的集合中包含的是数组类型的元素,
	 *   每个对象数组代表查询结果的一条记录可以在持久化类中定义一个对象的构造器来包装投影查询返回的记录,
	 *   使程序代码能完全运用面向对象的语义来访问查询结果集.可以通过 DISTINCT 关键字来保证查询结果不会返回重复元素
	 */
	@Test
	public void testFieldQuery(){
		String hql = "SELECT e.email, e.salary, e.dept FROM Employee e WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Object[]> result = query.setEntity("dept", dept)
				                     .list();
		
		for(Object [] objs: result){
			System.out.println(Arrays.asList(objs));
		}
	}


	/**
	 * 命名查询:
	 * Hibernate 允许!!在映射文件!!中定义字符串形式的查询语句.<query> 元素用于定义一个 HQL 查询语句, 它和 <class> 元素并列.
	 */
	@Test
	public void testNamedQuery(){
		Query query = session.getNamedQuery("salaryEmps");
		
		List<Employee> emps = query.setFloat("minSal", 5000)
				                   .setFloat("maxSal", 10000)
				                   .list();
		
		System.out.println(emps.size()); 
	}
	/**
	 * 分页查询:
	 * setFirstResult(int firstResult): 设定从哪一个对象开始检索, 参数 firstResult 表示这个对象在查询结果中的索引位置,
	 * 									索引位置的起始值为 0. 默认情况下, Query 从查询结果中的第一个对象开始检索
	 * setMaxResults(int maxResults): 设定一次最多检索出的对象的数目. 在默认情况下, Query 和 Criteria 接口检索出查询结果中所有的对象
	 */
	@Test
	public void testPageQuery(){
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		
		int pageNo = 22;
		int pageSize = 5;
		
		List<Employee> emps = 
								query.setFirstResult((pageNo - 1) * pageSize)
								     .setMaxResults(pageSize)
								     .list();
		System.out.println(emps);
	}
	
	@Test
	public void testHQLNamedParameter(){
		//1. 创建 Query 对象
		//基于命名参数. 
		String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		query.setFloat("sal", 7000)
		     .setString("email", "%A%");
		
		//3. 执行查询
		List<Employee> emps = query.list();
		System.out.println(emps.size());  
	}
	
	@Test
	public void testHQL(){
		//1. 创建 Query 对象
		//基于位置的参数. 
		String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
				+ "ORDER BY e.salary";
		Query query = session.createQuery(hql);
		
		//2. 绑定参数
		//Query 对象调用 setXxx 方法支持方法链的编程风格.
		Department dept = new Department();
		dept.setId(80); 
		query.setFloat(0, 6000)
		     .setString(1, "%A%")
		     .setEntity(2, dept);
		
		//3. 执行查询
		List<Employee> emps = query.list();
		System.out.println(emps.size());  
	}
	
	@Test
	public void test(){

	}

}
