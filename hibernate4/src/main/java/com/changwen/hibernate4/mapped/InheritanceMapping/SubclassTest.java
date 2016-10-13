package com.changwen.hibernate4.mapped.InheritanceMapping;

import com.changwen.hibernate4.mapped.InheritanceMapping.pojo.Person;
import com.changwen.hibernate4.mapped.InheritanceMapping.pojo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Hibernate
 *
 * @author lcw 2015/12/23
 */
public class SubclassTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
/**
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
*/
    @After
    public void destroy(){
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
    /**
     * Hibernate支持三种继承映射策略：
     * 1、使用 subclass 进行映射：将域模型中的每一个实体对象映射到一个独立的表中，
     *   也就是说不用在关系数据模型中考虑域模型中的继承关系和多态。
     * 2、使用 joined-subclass 进行映射： 对于继承关系中的子类使用同一个表，
     *   这就需要在数据库表中增加额外的区分子类类型的字段。
     * 3、使用 union-subclass 进行映射：域模型中的每个类映射到一个表，通过关系数据模型中的外键来描述表之间的继承关系。
     *   这也就相当于按照域模型的结构来建立数据库中的表，并通过外键来建立表之间的继承关系。
     */

    /**
     * 缺点:
     * 1. 使用了辨别者列.
     * 2. 子类独有的字段不能添加非空约束.
     * 3. 若继承层次较深, 则数据表的字段也会较多.
     */

    /**
     * 查询:
     * 1. 查询父类记录, 只需要查询一张数据表
     * 2. 对于子类记录, 也只需要查询一张数据表
     */
    @Test
    public void testQuery(){
        List<Person> persons = session.createQuery("FROM Person").list();
        System.out.println(persons.size());

        List<Student> stus = session.createQuery("FROM Student").list();
        System.out.println(stus.size());
    }

    /**
     * 插入操作:
     * 1. 对于子类对象只需把记录插入到一张数据表中.
     * 2. 辨别者列有 Hibernate 自动维护.
     */
    @Test
    public void testSave(){
        Person person = new Person();
        person.setAge(11);
        person.setName("AA");
        session.save(person);

        Student stu = new Student();
        stu.setAge(22);
        stu.setName("BB");
        stu.setSchool("ATGUIGU");
        session.save(stu);
    }

}

