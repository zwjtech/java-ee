package com.changwen.mybatis;

import com.changwen.mybatis.bean.*;
import com.changwen.mybatis.annotation.UserMapperAnnotation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>function:</b>
 *
 * @author liucw on 2016/3/17.
 */
public class MybatisTest {
    private SqlSessionFactory factory;
    private SqlSession session;

    @Before
    public void init() {
        //加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream inputStream = MybatisTest.class
                .getClassLoader().getResourceAsStream("conf.xml");

        //构建SqlSessionFactory
        factory = new SqlSessionFactoryBuilder().build(inputStream);

        //创建能执行映射文件中sql的sqlSession
        //默认是手动提交的,需要设置为true
        session = factory.openSession(true);
    }

    @Test
    public void testHelloWorld() {
        //是userMapper1.xml里的namespace + select里的id里的值
        String statement = "com.changwen.mybatis.bean.userMapper1" + ".getUserHelloWord";

        //执行查询返回一个唯一user对象的sql
        User user = session.selectOne(statement, 2);
        System.out.println(user);
    }

    /***************
     * 测试: CRUD操作的XML的实现**************8
     */
    @Test
    public void testAdd() {
        String statement = "com.changwen.mybatis.bean.userMapper1.addUser";
        int insert = session.insert(statement, new User(3, "KK", 23));
        //提交,如果session = factory.openSession();，则需要手动提交
        //session.commit();
        session.close();
        System.out.println(insert); //1
    }

    @Test
    public void testUpdate() {
        String statement = "com.changwen.mybatis.bean.userMapper1.updateUser";

        int update = session.update(statement, new User(3, "KK444", 25));

        session.close();
        System.out.println(update); //1
    }

    @Test
    public void testDelete() {
        String statement = "com.changwen.mybatis.bean.userMapper1.deleteUser";

        int delete = session.delete(statement, 3);

        session.close();
        System.out.println(delete);
    }

    @Test
    public void testGetUser() {
        String statement = "com.changwen.mybatis.bean.userMapper1.getUser";

        User user = session.selectOne(statement, 1);

        session.close();
        System.out.println(user);
    }

    @Test
    public void testGetAll() {
        String statement = "com.changwen.mybatis.bean.userMapper1.getAllUsers";

        List<User> list = session.selectList(statement);

        session.close();
        System.out.println(list);
    }

    /**********
     * 测试: CRUD操作的注解的实现
     ***********/
    @Test
    public void testAddByAnnotation() {
        UserMapperAnnotation mapper = session.getMapper(UserMapperAnnotation.class);
        int add = mapper.add(new User(4, "an", 45));
        System.out.println(add);

        session.close();
    }

    /****
     * 测试:　解决字段名与实体类属性名不相同的冲突
     */
    @Test
    public void main() {
        String statement = "com.changwen.mybatis.bean.orderMapper.getOrder";
        statement = "com.changwen.mybatis.bean.orderMapper.getOrder2";
        Order order = session.selectOne(statement, 2);
        System.out.println(order);
    }

    /**
     * 测试: 一对一关联表查询
     * 老师和班级
     */
    @Test
    public void testOneToOne() {
        String statement = "com.changwen.mybatis.bean.OneToOneMapper.getClass";
        statement = "com.changwen.mybatis.bean.OneToOneMapper.getClass2";

        Classes c = session.selectOne(statement, 2);
        System.out.println("--" + c);

        session.close();
    }

    /**
     * 测试: 一对多关联表查询
     * 班级和学生
     */
    @Test
    public void testOneToMany() {
        String statement = "com.changwen.mybatis.bean.OneToManyMapper.getClass";
        statement = "com.changwen.mybatis.bean.OneToManyMapper.getClass2";

        Classes c = session.selectOne(statement, 2);
        System.out.println("---" + c);
        session.close();
    }

    /**
     * 测试: 动态SQL与模糊查询
     * 查询条件的实体类：ConditionUser
     *
     * MyBatis中可用的动态SQL标签
     * if、choose(when,otherwise) 、trim(where,set)、foreach
     */
    @Test
    public void test222() {
        String statement = "com.changwen.mybatis.bean.userMapper1.getUserByCondition";
        String name = "o";
        name = null;

        ConditionUser parameter = new ConditionUser("%" + name + "%", 13, 18);
        List<User> list = session.selectList(statement, parameter);
        System.out.println("---" + list);
        session.close();
    }

    /**
     * 测试调用存储过程
     *
     * 提出需求:查询得到男性或女性的数量, 如果传入的是0就女性否则是男性
     */
    @Test
    public void test8() {
        String statement = "com.changwen.mybatis.bean.userMapper1.getUserCount";

        Map<String, Integer> parameterMap = new HashMap<String, Integer>();
        parameterMap.put("sexid", 0);
        parameterMap.put("usercount", -1);


        session.selectOne(statement, parameterMap);

        Integer result = parameterMap.get("usercount");
        System.out.println(result);

        session.close();
    }

    /**
     * 正如大多数持久层框架一样，MyBatis 同样提供了一级缓存和二级缓存的支持
     1.一级缓存: 基于PerpetualCache 的 HashMap本地缓存，其存储作用域为 Session，!!!
        当 Session flush 或 close 之后，该Session中的所有 Cache 就将清空。
     2. 二级缓存与一级缓存其机制相同，默认也是采用 PerpetualCache，HashMap存储，
        不同!!! 在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源，如 Ehcache。
     3. 对于缓存数据更新机制，当某一个作用域(一级缓存Session/二级缓存Namespaces)的进行了 C/U/D 操作后，
        默认该作用域下所有 select 中的缓存将被clear。
     */
    /*
 * 测试缓存
 1. 一级缓存 : session级的缓存:什么时候清除缓存
 	1. 执行了session.clearCache();
 	2. 执行CUD操作
 	3. 不是同一个Session对象
 2. 二级缓存: 是一个映射文件级的缓存;而Hibernate是一SessionFactory级别的缓存
 */
    @Test
    public void testCacheOne() {
        String statement = "com.changwen.mybatis.bean.userMapper1.getUserByCache";
        CUser user = session.selectOne(statement, 1);
        System.out.println(user);

        user = session.selectOne(statement, 1);
        System.out.println(user); //与上面结果一样，只有一条sql语句
        System.out.println("----------------");

		/*
         //1. 执行了session.clearCache();
		session.clearCache();
		*/

        /*
		//2. 执行CUD操作
		session.update("com.changwen.mybatis.bean.userMapper1.updateUserByCache", new CUser(1, "Tom", 13));
		session.commit();
		*/

       /* //3. 不是同一个Session对象
        session.close();
        session = factory.openSession();
        user = session.selectOne(statement, 1);
        System.out.println(user);*/

        session.close();
    }

    /**
     * 二级缓存的CUser类型要实现Serializable
     */
    @Test
    public void testCacheTwo() {
        SqlSession session1 = factory.openSession();
        SqlSession session2 = factory.openSession();

        String statement = "com.changwen.mybatis.bean.userMapper1.getUserByCache";

        CUser user = session1.selectOne(statement, 1);
        session1.commit();
        System.out.println(user);

        user = session2.selectOne(statement, 1);
        session2.commit();
        System.out.println(user);
    }


}