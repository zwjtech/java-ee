package Spring4_IOC;

import Spring4_IOC.bean.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


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
    /***********配置Bean：
     *          配置形式：基于 XML 文件的方式
     *          配置方式：通过全类名（反射）***************/

    //测试依赖注入
    @Test
    public void testDependencyInjection() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_IOC/applicationContext.xml");
        Car car = (Car) ctx.getBean("car");
        System.out.println(car);

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

        //测试使用List集合属性
        PersonCarList person3_2 = (PersonCarList) ctx.getBean("personCarList");
        System.out.println("测试单例的集合bea: " + person3_2);

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
