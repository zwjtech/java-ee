package Spring4_IOC;

import Spring4_IOC.annotation.TestObject;
import Spring4_IOC.annotation.controler.UserController;
import Spring4_IOC.annotation.repository.UserRepository;
import Spring4_IOC.annotation.service.UserService;
import Spring4_IOC.bean.*;
import Spring4_IOC.bean.CarCycle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;


/**
 * Spring 提供了两种类型的 IOC 容器实现.
 BeanFactory: IOC 容器的基本实现.
 ApplicationContext: 提供了更多的高级特性. 是 BeanFactory 的子接口.
 BeanFactory 是 Spring 框架的基础设施，面向 Spring 本身；
 ApplicationContext 面向使用 Spring 框架的开发者，几乎所有的应用场合都直接使用 ApplicationContext 而非底层的 BeanFactory
 无论使用何种方式, 配置文件时相同的.
 * @author Administrator on 2016/3/5.
 */
public class Spring4_IOCTest {
    /**
     * Spring 4.x 新特性：泛型依赖注入
     */
    @Test
    public void testDI() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-generic.xml");
        Spring4_IOC.di.UserService userService = (Spring4_IOC.di.UserService) ctx.getBean("userServiceTest");
        System.out.println(userService);
        userService.add();

    }
    /**
     * 特定组件包括:
     * Component: 基本注解, 标识了一个受 Spring 管理的组件
     * Respository: 标识持久层组件
     * Service: 标识服务层(业务层)组件: UserServiceImpl userService
     * Controller: 标识表现层组件
     *
     * 对于扫描到的组件, Spring 有默认的命名策略:
     * 使用非限定类名, 第一个字母小写!!!. 也可以在注解中通过 value 属性值标识组件的名称
     *
     * 还需要在 Spring 的配置文件中声明 <context:component-scan>
     */
    @Test
    public void testAnnotation() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-annotation.xml");

        TestObject to = (TestObject) ctx.getBean("testObject");
        System.out.println(to);

        //类UserController第一个字母小写 userController
        UserController userController = (UserController) ctx.getBean("userController");
        System.out.println(userController);
        userController.execute();

        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);

        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        System.out.println(userRepository);
    }


    /**********Bean配置形式：
     *         一、基于 XML 文件的方式；
     *         二、基于注解的方式（基于注解配置 Bean；基于注解来装配 Bean 的属性）
     * 下面的方法都是使用的基于 XML 文件的方式*************/


    /**
     * Bean 的配置方式：
     * -、通过全类名（反射）；
     * 二、通过工厂方法（静态工厂方法 & 实例工厂方法）；
     * 三、FactoryBean
     * 测试FactoryBean
     * Spring 中有两种类型的 Bean, 一种是普通Bean, 另一种是工厂Bean, 即FactoryBean
     */
    @Test
    public void testFactoryBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-beanFactory.xml");
//        CarFactoryBean car = (CarFactoryBean) ctx.getBean("car");
        Car car = (Car) ctx.getBean("car");
        System.out.println(car);
    }

    /**
     * Bean 的配置方式：-、通过全类名（反射）；
     *二、通过工厂方法（静态工厂方法 & 实例工厂方法）；
     * 三、FactoryBean
     * 静态工厂方法：直接调用某一个类的静态方法就可以返回Bean实例
     * 实例工厂方法：实例工厂的方法，即需要先创建工厂本身，再调用工厂的实例方法来返回bean的实例
     */
    @Test
    public void testStaticFactory() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-factory.xml");
        //测试静态工厂方法
        Car car1 = (Car) ctx.getBean("car1");
        System.out.println(car1);

        //实例工厂方法
        Car car2 = (Car) ctx.getBean("car2");
        System.out.println(car2);
    }

    /**
     * IOC 容器中 Bean 的生命周期
     * Spring IOC 容器对 Bean 的生命周期进行管理的过程:
     * -通过构造器或工厂方法创建 Bean 实例
     * -为 Bean 的属性设置值和对其他 Bean 的引用
     * -调用 Bean 的初始化方法 !!
     * -Bean 可以使用了
     * -当容器关闭时, 调用 Bean 的销毁方法 !!
     *
     * 在 Bean 的声明里设置 init-method 和 destroy-method 属性, 为 Bean 指定初始化和销毁方法.
     */
    @Test
    public void testCycle() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-cycle.xml");
        CarCycle car = (CarCycle) ctx.getBean("car");
        System.out.println(car);

        //关闭IOC容器
        ctx.close();
    }
    /**
     * 测试SpEL
     */
    @Test
    public void testSpEL() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-spel.xml");
        Address address = (Address) ctx.getBean("address");
        System.out.println(address);

        Car car1 = (Car) ctx.getBean("car");
        System.out.println(car1);

        PersonSpEL person = (PersonSpEL) ctx.getBean("person");
        System.out.println(person);
    }
    /**
     * 使用外部属性文件
     */
    @Test
    public void testProperties() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-properties.xml");
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        System.out.println(dataSource);
    }
    /**
     * bean 的作用域：
     * singleton：默认值，容器初始时创建bean实例，在整个容器的生命周期内只创建这一个bean.单例的
     * prototype：原型的，容器初始化时不创建bean的实例，而在每次请求时都创建一个新的bean实例!!，并返回
     * WEB 环境作用域(request,sessionPojo)
     */
    @Test
    public void testScope() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-scope.xml");

        Car2 car1 = (Car2) ctx.getBean("car");
        Car2 car2 = (Car2) ctx.getBean("car");
        System.out.println(car1 == car2); //默认是singleton，结果为true
    }

    /**
     * bean 之间的关系：继承；依赖
     */
    @Test
    public void testRelation() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-relation.xml");
        //测试继承
        Address address = (Address) ctx.getBean("address");
        System.out.println(address);

        Address address2 = (Address) ctx.getBean("address2");
        System.out.println(address2);

        //测试依赖
        PersonAutowire person = (PersonAutowire) ctx.getBean("person");
        System.out.println("测试依赖: " + person);
    }

    /**
     * 自动装配 Bean
     * byType(根据类型自动装配)
     * byName(根据名称自动装配)
     * constructor(通过构造器自动装配):不推荐使用
     */
    @Test
    public void testAutowire() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/beans-autowire.xml");
        //测试自动装配byName
        PersonAutowire person = (PersonAutowire) ctx.getBean("person");
        System.out.println("测试自动装配byName: " + person);

        //测试自动装配byType
        PersonAutowire person2 = (PersonAutowire) ctx.getBean("person2");
        System.out.println("测试自动装配byType: " + person2);
    }

    /***********
     * Bean 的配置方式：-、通过全类名（反射）；二、通过工厂方法（静态工厂方法 & 实例工厂方法）；三、FactoryBean
     *
     * 配置Bean：配置方式：通过全类名（反射）***************/
    //测试依赖注入
    @Test
    public void testDependencyInjection() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/applicationContext.xml");
        Car car = (Car) ctx.getBean("car");
        System.out.println(car.toString());

        //测试car里有多个构造器，如何配置IOC
        Car car2 = (Car) ctx.getBean("car2");
        System.out.println(car2);

        //测试 Person类里有一个属性是Car如何配置，用ref建立bean之间的引用关系
        Person person = (Person) ctx.getBean("person");
        System.out.println("测试ref: " + person);

        //测试级联属性
        Person person2 = (Person) ctx.getBean("person2");
        System.out.println("测试级联属性: " + person2);


        //测试使用List集合属性
        PersonCarList person3 = (PersonCarList) ctx.getBean("person3");
        System.out.println("测试使用List集合属性: " + person3);

        //测试使用单例List集合属性
        PersonCarList person3_2 = (PersonCarList) ctx.getBean("personCarList");
        System.out.println("测试单例的List集合: " + person3_2);

        //测试使用Map集合属性
        PersonCarMap person4 = (PersonCarMap) ctx.getBean("person4");
        System.out.println("测试使用Map集合属性: " + person4);

        //测试使用Properties集合属性，这里一般是用来测试数据库是否连接成功！！！
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        System.out.println("测试使用Properties集合属性: " + dataSource);

        //测试通过P命名空间为bean的属性赋值
        PersonCarList person5 = (PersonCarList) ctx.getBean("person5");
        System.out.println("测试通过P命名空间为bean的属性赋值: " + person5);
    }


    @Test
    public void testHelloWorld() {
        //        前两行可以用Spring来实现
//        HelloWorldBean helloWorldBean = new HelloWorldBean();
//        helloWorldBean.setUser("Tom");
//        helloWorldBean.hello();

        //1. 创建 Spring 的 IOC 容器(ApplicationContext代表Spring 的 IOC 容器）
        //创建的时候会先调用无参构造器，同时会调用 setter方法对属性赋值!!
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/applicationContext.xml");

        //2. 从 IOC 容器中的id获取 bean 的实例
        HelloWorldBean helloWorldBean = (HelloWorldBean) ctx.getBean("helloWorld");

        //根据类型来获取 bean 的实例: 要求在  IOC 容器中只有一个与之类型匹配的 bean, 若有多个则会抛出异常.
        //一般情况下, 该方法可用, 因为一般情况下, 在一个 IOC 容器中一个类型对应的 bean 也只有一个.
//       HelloWorldBean helloWorld1 = ctx.getBean(HelloWorldBean.class);

        //3. 使用 bean
        helloWorldBean.hello();
    }
}
