package com.changwen.hibernate4.session;

import com.changwen.hibernate4.session.session.News;
import com.changwen.hibernate4.session.session.News2;
import com.changwen.hibernate4.session.session.Pay;
import com.changwen.hibernate4.session.session.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * Hibernate:Java 领域的持久化框，也是一个 ORM 框架
 * ORM(Object/Relation Mapping): 对象/关系映射
 *   ORM 主要解决对象-关系的映射
 *   面向对象概念      面向关系概念
 *   类              表
 *   对象             表的行（记录）
 *   属性             表的列（字段）
 * ORM的思想：将关系数据库中表中的记录映射成为对象，以对象的形式展现，！！程序员可以把对数据库的操作转化为对对象的操作！！。
 * ORM 采用元数据来描述对象-关系映射细节, 元数据通常采用 XML 格式, 并且存放在专门的对象-关系映射文件中.

 *
 * @author liucw on 2016/3/13.
 */
public class SessionTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){
        // 创建 Configuration 对象: 对应 hibernate 的基本配置信息和 对象关系映射信息
        Configuration configuration = new Configuration().configure();

        // 创建一个 ServiceRegistry 对象: hibernate 4.x 新添加的对象
        //hibernate 的任何配置和服务都需要在该对象中注册后才能有效.
        ServiceRegistry serviceRegistry =
                new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                        .buildServiceRegistry();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        //创建一个 Session 对象
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();
    }

    @After
    public void destroy(){
        //提交事务 :真正与数据库交互的语句
        transaction.commit();

        //关闭 Session
        session.close();

        //关闭 SessionFactory 对象
        sessionFactory.close();
    }

    @Test
    public void testComponent(){
        Worker worker = new Worker();
        Pay pay = new Pay();

        pay.setMonthlyPay(1000);
        pay.setYearPay(80000);
        pay.setVocationWithPay(5);

        worker.setName("ABCD");
        worker.setPay(pay);

        session.save(worker);
    }

    @Test
    public void testBlob() throws Exception{
//		News news = new News();
//		news.setAuthor("cc");
//		news.setContent("CONTENT");
//		news.setDate(new Date());
//		news.setDesc("DESC");
//		news.setTitle("CC");
//
//		InputStream stream = new FileInputStream("Hydrangeas.jpg");
//		Blob image = Hibernate.getLobCreator(session)
//				              .createBlob(stream, stream.available());
//		news.setImage(image);
//
//		session.save(news);

        News2 news = (News2) session.get(News.class, 1);
       // Blob image = news.getImage();

        //InputStream in = image.getBinaryStream();
      //  System.out.println(in.available());
    }

    @Test
    public void testPropertyUpdate(){
        News2 news = (News2) session.get(News.class, 1);
        news.setTitle("aaaa");

        //System.out.println(news.getDesc());
        System.out.println(news.getOccurrenceDate().getClass());
    }

    @Test
    public void testIdGenerator() throws InterruptedException{
        News news = new News("AA", "aa", new java.sql.Date(new Date().getTime()));
        session.save(news);

//		Thread.sleep(5000);
    }

    @Test
    public void testDynamicUpdate(){
        News news = (News) session.get(News.class, 1);
        news.setAuthor("AABCD");

    }


    /*****************************这里的配置文件只有hibernate.cfg.xml********************************/
    /********************************session结束**********************
     * 通过 Hibernate 调用存储过程
     * Work 接口: 直接通过 JDBC API 来访问数据库的操作
     */
    @Test
    public void testDoWork(){
        session.doWork(new Work() {

            //@Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);

                //调用存储过程.
            }
        });
    }

    /**
     * 持久化对象的状态
     * 站在持久化的角度, Hibernate 把对象分为 4 种状态:
     * 临时状态, 持久化状态, 游离状态, 删除状态.
     * Session 的特定方法能使对象从一个状态转换到另一个状态.
     * 临时状态-->持久化状态：save()、saveOrUpdate()、persist()、merge()
     * 持久化状态-->游离状态: evict()、close()、clear()
     * 持久化状态-->删除状态: delete()
     * 游离状态-->持久化状态: update()、saveOrUpdate()、merge()
     */
    /**
     * evict: 从 session缓存中!!!把指定的持久化对象移除
     */
    @Test
    public void testEvict(){
        News news1 = (News) session.get(News.class, 1);
        News news2 = (News) session.get(News.class, 2);

        news1.setTitle("AA");
        news2.setTitle("BB");

        session.evict(news1);
    }

    /**
     * delete: 执行删除操作. 只要 OID 和数据表中一条记录对应, 就会准备执行 delete 操作
     *         若 OID 在数据表中没有对应的记录, 则抛出异常
     *
     * 可以通过设置 hibernate 配置文件 hibernate.use_identifier_rollback 为 true,
     * 使删除对象后, 把其 OID 置为  null
     */
    @Test
    public void testDelete(){
//		News news = new News();
//		news.setId(1);

        News news = (News) session.get(News.class, 163840);
        session.delete(news);

        System.out.println(news);
    }

    /**
     * 注意:
     * 1. 若 OID 不为 null, 但数据表中还没有和其对应的记录. 会抛出一个异常.
     * 2. 了解: OID 值等于 id 的 unsaved-value 属性值的对象, 也被认为是一个游离对象
     */
    @Test
    public void testSaveOrUpdate(){
        News news = new News("FFF", "fff", new Date());
        news.setId(11);

        session.saveOrUpdate(news);
    }
    /**
     * update:
     * 什么时候用？
     * 1. 若更新一个持久化对象, 不需要显示的调用 update 方法.
     *    因为在调用 Transaction的 commit() 方法时, 会先执行 session 的 flush 方法.
     * 2. 更新一个游离对象, 需要显式的调用 session 的 update 方法.
     *    可以把一个游离对象变为持久化对象
     *
     * 需要注意的:
     * 1. 无论要更新的游离对象和数据表的记录是否一致, 都会发送 UPDATE 语句.
     *    如何能让 updat 方法不再盲目的出发 update 语句呢 ? 在 .hbm.xml 文件的 class 节点设置
     *    select-before-update=true (默认为 false). 但通常不需要设置该属性.
     *
     * 2. 若数据表中没有对应的记录, 但还调用了 update 方法, 会抛出异常
     *
     * 3. 当 update() 方法关联一个游离对象时,
     *    如果在 Session 的缓存中已经存在相同 OID 的持久化对象, 会抛出异常.
     *    因为在 Session 缓存中不能有两个 OID 相同的对象!
     *
     * 下面的方法测试要一个个的测试，其它的要关闭
     */
    @Test
    public void testUpdate(){
        //若更新一个持久化对象, 不需要显示的调用 update 方法
        News news = (News) session.get(News.class, 1);
        news.setAuthor("changwen1");
        //session.update(news);  //这个update方法没必要写

        //更新一个游离对象, 需要显式的调用 session 的 update 方法.
        News news2 = (News) session.get(News.class, 1);
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        news2.setAuthor("changwen2"); //这时这里的new2为游离对象
        session.update(news2);  //需要显式的调用

        News news3 = (News) session.get(News.class, 1);
        session.update(news); //会抛出异常

    }

    /**
     * get VS load:
     *
     * 1. 执行 get 方法: 会立即加载对象.
     *    执行 load 方法, 若不适用该对象, 则不会立即执行查询操作, 而返回一个代理对象
     *
     *    get 是 立即检索, load 是延迟检索.
     *
     * 2.若数据表中没有对应的记录, Session 也没有被关闭.
     *    get 返回 null
     *    load 若不使用该对象的任何属性, 没问题; 若需要初始化了, 抛出异常.
     *
     * 3.load 方法可能会抛出 LazyInitializationException 异常:
     *      在需要初始化代理对象之前已经关闭了 Session
     *
     *  这里测试的时候需要把不必要的注释掉
     */
    @Test
    public void testLoad(){
        News news = (News) session.load(News.class, 1); //load 是延迟检索,用的时候才检索
        System.out.println(news.getClass().getName()); //返回一个代理对象

        //如何数据库里没有id为10的数据，load方法会抛出异常
        News news2 = (News) session.load(News.class, 10);
        System.out.println(news2);

        //这里会抛出懒加载异常
        News news3 = (News) session.load(News.class, 1);
        session.close();
        System.out.println(news3);
    }
    @Test
    public void testGet(){
        News news = (News) session.get(News.class, 1);
        System.out.println(news);

        //如果数据库里没有id为10的数据，get方法会输出null
        News news2 = (News) session.get(News.class, 10);
        System.out.println(news2);

        //这里输出结果为Null
        News news3 = (News) session.get(News.class, 1);
        session.close();
        System.out.println(news3);
    }

    /**
     * persist(): 也会执行 INSERT 操作
     *
     * 和 save() 的区别 :
     * 在调用 persist 方法之前, 若对象已经有 id 了, 则不会执行 INSERT, 而抛出异常
     */
    @Test
    public void testPersist(){
        News news = new News();
        news.setTitle("EE");
        news.setAuthor("ee");
        news.setOccurrenceDate(new Date());
        news.setId(200);//在调用 persist 方法之前, 若对象已经有 id 了, 则不会执行 INSERT, 而抛出异常

        session.persist(news);
    }

    /**
     * 1. save() 方法
     * 1). 使一个临时对象变为持久化对象
     * 2). 为对象分配 ID.！！！
     * 3). 在 flush 缓存时会发送一条 INSERT 语句.
     * 4). 在 save 方法之前的 id 是无效的
     * 5). 持久化对象的 ID 是不能被修改的!
     */
    @Test
    public void testSave(){
        News news = new News();
        news.setTitle("Oracle");
        news.setAuthor("ora");
        news.setOccurrenceDate(new Date());
        //news.setId(100);  //在 save 方法之前的 id 是无效的

        System.out.println(news);//这里输出的id=null
        session.save(news);
        System.out.println(news);//这里id有值
        //news.setId(101);  //持久化对象的 ID 是不能被修改的!
    }

    /********************************************************************************************
     * 对于同时运行的多个事务, 当这些事务访问数据库中相同的数据时, 如果没有采取必要的隔离机制, 就会导致各种并发问题:
     *  脏读: 对于两个事物 T1, T2, T1 读取了已经被 T2 更新但还没有被提交的字段. 之后, 若 T2 回滚, T1读取的内容就是临时且无效的.
     *  不可重复读: 对于两个事物 T1, T2, T1 读取了一个字段, 然后 T2 更新了该字段. 之后, T1再次读取同一个字段, 值就不同了.
     *  幻读: 对于两个事物 T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的行. 之后, 如果 T1 再次读取同一个表, 就会多出几行.
     *
     * 数据库事务的隔离性: 数据库系统必须具有隔离并发运行各个事务的能力, 使它们不会相互影响, 避免各种并发问题.
     * 一个事务与其他事务隔离的程度称为隔离级别. 数据库规定了多种事务隔离级别, 不同隔离级别对应不同的干扰程度, 隔离级别越高, 数据一致性就越好, 但并发性越弱
     */
    /**
     * 操作 Session 缓存有三个方法：flush()、refresh()、clear()
     */
    /**
     * clear(): 清理缓存
     */
    @Test
    public void testClear(){
        News news1 = (News) session.get(News.class, 1);

        session.clear();

        News news2 = (News) session.get(News.class, 1);
        //会有两条select语句
    }

    /**
     * refresh(): 会强制发送 SELECT 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致!
     */
    @Test
    public void testRefresh(){
        News news = (News) session.get(News.class, 1);
        System.out.println(news);

        //session.flush();   //这里不会发送select语句
        session.refresh(news);  //这里会强制发送select语句
        System.out.println(news);
    }

    /**
     * flush(): 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
     * 1. 在 Transaction 的 commit() 方法中: 先调用 session 的 flush 方法, 再提交事务
     * 2. flush() 方法会可能会发送 SQL 语句, 但不会提交事务!!!
     *
     * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
     * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作, 以得到数据表的最新的记录
     * 2). 例外的情况：若记录的 ID 是由底层数据库使用自增的方式生成的, 则在调用 save() 方法时, 就会立即发送 INSERT 语句.
     *      因为 save 方法后, 必须保证对象的 ID 是存在的!
     */
    @Test
    public void testSessionFlush2(){
        News news = new News("Java", "SUN", new Date());
        session.save(news);
    }
    @Test
    public void testSessionFlush(){
        //这两条语句会执行两条sql语句：一条select,一条update(默认执行update语句)
        News news = (News) session.get(News.class, 1);
        news.setAuthor("Oracle");

//      session.flush();
//      System.out.println("flush");

        News news2 = (News) session.createCriteria(News.class).uniqueResult();
        System.out.println(news2);
    }

    /**
     * Session 缓存. 只要 Session 实例没有结束生命周期,
     * 且没有清理缓存，则存放在它缓存中的对象也不会结束生命周期
     *
     * Session 缓存可减少 Hibernate 应用程序访问数据库的频率。
     */
    @Test
    public void testSessionCache(){
        News news = (News) session.get(News.class, 1);
        System.out.println(news);

        News news2 = (News) session.get(News.class, 1);
        System.out.println(news2);

        //只发了一条select语句
        System.out.println(news == news2); //true
    }

    @Test
    public void test() {

    }

}
