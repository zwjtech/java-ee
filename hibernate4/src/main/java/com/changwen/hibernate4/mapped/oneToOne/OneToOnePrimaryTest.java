package com.changwen.hibernate4.mapped.oneToOne;

import com.changwen.hibernate4.mapped.oneToOne.pojo.Department;
import com.changwen.hibernate4.mapped.oneToOne.pojo.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * OneToManyBothTest
 *
 * @author lcw 2015/12/23
 */
public class OneToOnePrimaryTest {

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
     * 基于主键映射的 1-1
     * 于主键的映射策略:指一端的主键生成器使用 foreign 策略,表明根据”对方”的主键来生成自己的主键，自己并不能独立生成主键.
     * <param> 子元素指定使用当前持久化类的哪个属性作为 “对方”
     *
     * 采用foreign主键生成器策略的一端增加 one-to-one 元素映射关联属性，其one-to-one属性还应增加 constrained=“true” 属性；
     * 另一端增加one-to-one元素映射关联属性。
     *
     * constrained(约束):指定为当前持久化类对应的数据库表的主键添加一个外键约束，
     * 引用被关联的对象(“对方”)所对应的数据库表主键
     */
    @Test
    public void testGet2(){
        //在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
        //并已经进行初始化.
        Manager mgr = (Manager) session.get(Manager.class, 1);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDept().getDeptName());
    }

    @Test
    public void testGet(){
        //1. 默认情况下对关联属性使用懒加载
        Department dept = (Department) session.get(Department.class, 1);
        System.out.println(dept.getDeptName());

        //2. 所以会出现懒加载异常的问题.
        Manager mgr = dept.getMgr();
        System.out.println(mgr.getMgrName());
    }

    @Test
    public void testSave(){

        Department department = new Department();
        department.setDeptName("DEPT-DD");

        Manager manager = new Manager();
        manager.setMgrName("MGR-DD");

        //设定关联关系
        manager.setDept(department);
        department.setMgr(manager);

        //保存操作
        //先插入哪一个都不会有多余的 UPDATE
        session.save(department);
        session.save(manager);

    }



}

