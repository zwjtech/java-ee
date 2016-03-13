package com.changwen.hibernate4.mapped.manyToOne;

import com.changwen.hibernate4.mapped.manyToOne.pojo.Customer;
import com.changwen.hibernate4.mapped.manyToOne.pojo.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManyToOneTest {
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
    public void testDelete(){
        //在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
        Customer customer = (Customer) session.get(Customer.class, 8);
        session.delete(customer); //不能删除
    }

    @Test
    public void testUpdate(){
        Order order = (Order) session.get(Order.class, 8);
        order.getCustomer().setCustomerName("AAA"); //有对应的update的语句
    }

    @Test
    public void testMany2OneGet(){
        //结论1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象.
        //      而没有查询关联的1 的那一端的对象!
//        Order order = (Order) sessionPojo.get(Order.class, 8);
//        System.out.println(order.getOrderName());
        //获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!
//        System.out.println(order.getCustomer().getClass().getName());

        //结论2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句.
//        Customer customer = order.getCustomer();
//        System.out.println(customer.getCustomerName());
        //上面1，2的输出结果顺序是:sql语句-->sout-->sql语句-->sout

        //3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时,
        //若此时 sessionPojo 已被关闭, 则默认情况下会发生 LazyInitializationException 异常
        Order order2 = (Order) session.get(Order.class, 8);
        System.out.println(order2.getOrderName());

        session.close(); //这里测试时需要把destory()方法里的transaction和session注解掉

        Customer customer2 = order2.getCustomer();
        System.out.println(customer2.getCustomerName());
        //上面3的输出结果顺序是:sql语句-->order2.getOrderName()

        //4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!
        Order order3 = (Order) session.get(Order.class, 8);
        System.out.println(order3.getCustomer().getClass().getName());
    }

    @Test
    public void testMany2OneSave(){
        Customer customer = new Customer();
        customer.setCustomerName("BB");

        Order order1 = new Order();
        order1.setOrderName("ORDER-3");
        Order order2 = new Order();
        order2.setOrderName("ORDER-4");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT
        //先插入 1 的一端, 再插入 n 的一端, 只有 INSERT 语句.
        //推荐先插入 1 的一端, 后插入 n 的一端
		session.save(customer);
		session.save(order1);
		session.save(order2);

        //先插入 Order, 再插入 Customer. 3 条 INSERT, 2 条 UPDATE
        //先插入 n 的一端, 再插入 1 的一端, 会多出 UPDATE 语句!
        //因为在插入多的一端时, 无法确定 1 的一端的外键值. 所以只能等 1 的一端插入后, 再额外发送 UPDATE 语句.
/*        sessionPojo.save(order1);
        sessionPojo.save(order2);
        sessionPojo.save(customer);*/
    }

    @Test
    public void test() {

    }

}

