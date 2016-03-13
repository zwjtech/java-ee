package com.changwen.hibernate4.mapped.oneToManyBoth;

import com.changwen.hibernate4.mapped.oneToManyBoth.pojo.Customer;
import com.changwen.hibernate4.mapped.oneToManyBoth.pojo.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneToManyBothTest {
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
    public void testCascade() {
        Customer customer = (Customer) session.get(Customer.class, 8);
        customer.getOrders().clear();
    }

    @Test
    public void testDelete(){
        //在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
        //如果有一的那端设置cascade 属性（级联属性），则可以删除
        Customer customer = (Customer) session.get(Customer.class, 8);
        session.delete(customer); //不能删除
    }

    @Test
    public void testUpdate(){
        Order order = (Order) session.get(Order.class, 8);
        order.getCustomer().setCustomerName("AAA"); //有对应的update的语句
    }

    @Test
    public void testGet() {
        //1.对n的那端的集合会有延迟加载，即只得到Customer的值，需要Order的时候才查
        Customer customer1 = (Customer) session.get(Customer.class, 10);
        System.out.println(customer1.getCustomerName());
        //2、返回的多的那端的集合是Hibernate 内置的集合类型
        //   该类型具有延迟加载 和 存放代理对象的功能
        System.out.println(customer1.getOrders().getClass());

        session.close();
        //3、可以会抛出LazyInitializationException 异常
        System.out.println(customer1.getOrders().size());

        //4、在需要使用集合元素的时候初始化。
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

    /**
     * 在hibernate中通过对 inverse 属性的来决定是由双向关联的哪一方来维护表和表之间的关系.
     * inverse = false 的为主动方，inverse = true 的为被动方, 由主动方负责维护关联关系
     *
     * 在没有设置 inverse=true 的情况下，父子两边都维护父子关系
     *
     * 在 1-n 关系中，将 n 方设为主控方将有助于性能改善!!
     */
    @Test
    public void testOneToManyBothSave(){
        Customer customer1 = new Customer();
        customer1.setCustomerName("AA");
        Order order1 = new Order();
        order1.setOrderName("ORDER-1");
        Order order2 = new Order();
        order2.setOrderName("ORDER-2");
        //设定关联关系
        order1.setCustomer(customer1);
        order2.setCustomer(customer1);
        customer1.getOrders().add(order1);
        customer1.getOrders().add(order2);

        /* 在没有设置inverse的情况下：先插入 Customer, 再插入 Order, 3 条 INSERT, 2 条update
           因为1的那端和n的那端都维护关联关系，所有会多出update
           如果在一的那端设置inverse=true，则只有3条insert!!
           */
		session.save(customer1);
		session.save(order1);
		session.save(order2);

        // 在没有设置inverse的情况下：先插入 Order, 再插入 Customer. 3 条 INSERT, 4 条 UPDATE
/*        sessionPojo.save(order1);
        sessionPojo.save(order2);
        sessionPojo.save(customer1);*/
    }

    //测试的时候，请注意配置文件里的各种设置，如inverse
    @Test
    public void test() {

    }

}

