package com.changwen.hibernate4.session;


import com.changwen.hibernate4.query.dao.DepartmentDao;
import com.changwen.hibernate4.query.entities.Department;
import com.changwen.hibernate4.query.entities.Employee;
import com.changwen.hibernate4.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * OneToManyBothTest
 *
 * @author lcw 2015/12/23
 */
public class SecondarySessionTest {
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

    @Test
    public void testBatch(){
        session.doWork(new Work() {
            //@Override
            public void execute(Connection connection) throws SQLException {
                //通过 JDBC 原生的 API 进行操作, 效率最高, 速度最快!
            }
        });
    }

    @Test
    public void testManageSession(){

        //获取 Session
        //开启事务
        Session session = HibernateUtils.getInstance().getSession();
        System.out.println("-->" + session.hashCode());
        Transaction transaction = session.beginTransaction();

        DepartmentDao departmentDao = new DepartmentDao();

        Department dept = new Department();
        dept.setName("ATGUIGU");

        departmentDao.save(dept);
        departmentDao.save(dept);
        departmentDao.save(dept);

        //若 Session 是由 thread 来管理的, 则在提交或回滚事务时, 已经关闭 Session 了.
        transaction.commit();
        System.out.println(session.isOpen());
    }

    @Test
    public void testQueryIterate(){
        Department dept = (Department) session.get(Department.class, 70);
        System.out.println(dept.getName());
        System.out.println(dept.getEmps().size());

        Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
//		List<Employee> emps = query.list();
//		System.out.println(emps.size());

        Iterator<Employee> empIt = query.iterate();
        while(empIt.hasNext()){
            System.out.println(empIt.next().getName());
        }
    }

    @Test
    public void testUpdateTimeStampCache(){
        Query query = session.createQuery("FROM Employee");
        query.setCacheable(true);

        List<Employee> emps = query.list();
        System.out.println(emps.size());

        Employee employee = (Employee) session.get(Employee.class, 100);
        employee.setSalary(30000);

        emps = query.list();
        System.out.println(emps.size());
    }

    @Test
    public void testQueryCache(){
        Query query = session.createQuery("FROM Employee");
        query.setCacheable(true);

        List<Employee> emps = query.list();
        System.out.println(emps.size());

        emps = query.list();
        System.out.println(emps.size());

        Criteria criteria = session.createCriteria(Employee.class);
        criteria.setCacheable(true);
    }

    @Test
    public void testCollectionSecondLevelCache(){
        Department dept = (Department) session.get(Department.class, 80);
        System.out.println(dept.getName());
        System.out.println(dept.getEmps().size());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Department dept2 = (Department) session.get(Department.class, 80);
        System.out.println(dept2.getName());
        System.out.println(dept2.getEmps().size());
    }

    @Test
    public void testHibernateSecondLevelCache(){
        Employee employee = (Employee) session.get(Employee.class, 100);
        System.out.println(employee.getName());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Employee employee2 = (Employee) session.get(Employee.class, 100);
        System.out.println(employee2.getName());
    }


}

