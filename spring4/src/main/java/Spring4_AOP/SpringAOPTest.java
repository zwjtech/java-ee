package Spring4_AOP;

import Spring4_AOP.aopAnnotation.ArithmeticCalculator;
import Spring4_AOP.aopAnnotation.ArithmeticCalculatorImpl;
import Spring4_AOP.aopAnnotation.ArithmeticCalculatorLoggingImpl;
import Spring4_AOP.aopAnnotation.ArithmeticCalculatorLoggingProxy;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 切面(Aspect):  横切关注点(跨越应用程序多个模块的功能)被模块化的特殊对象
 通知(Advice):  切面必须要完成的工作
 目标(Target): 被通知的对象
 代理(Proxy): 向目标对象应用通知之后创建的对象
 连接点（Joinpoint）：程序执行的某个特定位置：如类某个方法调用前、调用后、方法抛出异常后等。连接点由两个信息确定：方法表示的程序执行点；相对点表示的方位。例如 ArithmethicCalculator#add() 方法执行前的连接点，执行点为 ArithmethicCalculator#add()； 方位为该方法执行前的位置
 切点（pointcut）：每个类都拥有多个连接点：例如 ArithmethicCalculator 的所有方法实际上都是连接点，即连接点是程序类中客观存在的事务。AOP 通过切点定位到特定的连接点。类比：连接点相当于数据库中的记录，切点相当于查询条件。切点和连接点不是一对一的关系，一个切点匹配多个连接点，切点通过

 * AOP(Aspect-Oriented Programming, 面向切面编程): 是一种新的方法论,
 * 是对传统 OOP(Object-Oriented Programming, 面向对象编程) 的补充.
 * AOP 的主要编程对象是切面(aspect), 而切面模块化横切关注点.
 *
 * @author Administrator on 2016/3/6.
 */
public class SpringAOPTest {
    @Test
    public void testXML() {
        //1、创建Spring的IOC的容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_AOP/applicationContext-xml.xml");

        //2、从IOC容器中获取bean的实例
        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculatorXML");

        //3、使用bean
        int result = arithmeticCalculator.add(3, 3);
        System.out.println("result:" + result);

        arithmeticCalculator.div(10, 0);
    }

    @Test
    public void testAOPAnnotation() {
        //1、创建Spring的IOC的容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring4_AOP/applicationContext-aop.xml");

        //2、从IOC容器中获取bean的实例
        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) ctx.getBean("arithmeticCalculator");

        //3、使用bean
        int result = arithmeticCalculator.div(3, 0);
        System.out.println("result:" + result);
    }

    @Test
    public void testProxyAOP() {
        ArithmeticCalculator target = new ArithmeticCalculatorImpl();
        ArithmeticCalculator proxy = new ArithmeticCalculatorLoggingProxy(target).getLoggingProxy();

        int result = proxy.add(1,2);
        System.out.println("-->" + result);

        result = proxy.div(4,2);
        System.out.println("-->" + result);

        result = proxy.mul(4,2);
        System.out.println("-->" + result);
    }

    @Test
    public void testHelloWorld() {
        ArithmeticCalculator a = new ArithmeticCalculatorLoggingImpl();

        int result = a.add(1, 2);
        System.out.println("-->" + result);
    }
}
