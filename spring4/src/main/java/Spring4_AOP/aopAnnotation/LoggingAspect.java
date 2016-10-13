package Spring4_AOP.aopAnnotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AOP 的 helloWorld
 * 1. 加入 jar 包
 * 2. 在 Spring 的配置文件中加入 aop 的命名空间。
 *
 * 3. 基于注解的方式来使用 AOP
 * 3.1 在配置文件中配置自动扫描的包: <context:component-scan base-package="com.atguigu.spring.aop"></context:component-scan>
 * 3.2 加入使 AspjectJ 注解起作用的配置: <aop:aspectj-autoproxy></aop:aspectj-autoproxy>为匹配的类自动生成动态代理对象.
 *
 * 4. 编写切面类（把横切关注点的代码抽象到切面的类中）:
 * 4.1 一个一般的 Java 类
 * 4.2 在其中添加要额外实现的功能.
 *
 * 5. 配置切面
 * 5.1 切面必须是 IOC 中的 bean: 实际添加了 @Component 注解
 * 5.2 声明是一个切面: 添加 @Aspect
 * 5.3 声明通知: 即额外加入功能对应的方法！！.
 * 5.3.1 前置通知: @Before("execution(Spring4_AOP.aopAnnotation.*(int, int))")
 * @Before 表示在目标方法执行之前执行 @Before 标记的方法的方法体.
 * @Before 里面的是切入点表达式:
 *
 * 6. 在通知中访问连接细节: 可以在通知方法中添加 JoinPoint 类型的参数, 从中可以访问到方法的签名和方法的参数.
 *
 * 7. @After 表示后置通知: 在方法执行之后执行的代码.
 */

/**
 * LoggingAspectXML:把这个类声明为一个切面：
 * 1、需要把该类放入到IOC容器中@Component
 * 2、再声明为一个切面@Aspect
 * @author Lcw 2015/11/13
 */
//把这个类声明为一个切面：需要把该类放入到IOC容器中，再声明一个切面
/*1.通过添加 @Aspect 注解声明一个 bean 是一个切面!
  2.可以使用 @Order 注解指定切面的优先级, 值越小优先级越高
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {
    /**
     * 重用切入点定义
     * 定义一个方法，用于声明切入点表达式，一般地，该方法中再不需要添入其它的代码
     */
    @Pointcut("execution(* Spring4_AOP.aopAnnotation.*.*(..))")
    public void declareJoinPointerExpression() {}

    //1、前置通知： 在目标方法开始之前执行（就是要告诉该方法要在哪个类哪个方法前执行）
    //@Before("execution(public int Spring4_AOP.aopAnnotation.*.*(int ,int))")
    @Before("declareJoinPointerExpression()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object [] args = joinPoint.getArgs();

        System.out.println("The method " + methodName + " begins with " + Arrays.asList(args));
    }

    //2、后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    //注意，在后置通知中还不能访问目标执行的结果!!!，执行结果需要到返回通知里访问
    //@After("execution(* Spring4_AOP.aopAnnotation.*.*(..))")
    @After("declareJoinPointerExpression()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " ends");
    }

    //无论连接点是正常返回还是抛出异常, 后置通知都会执行. 如果只想在连接点返回的时候记录日志, 应使用返回通知代替后置通知.

    //3、返回通知:在方法正常结束后执行的代码，返回通知是可以访问到方法的返回值的！！！
    //@AfterReturning(value = "execution(* Spring4_AOP.aopAnnotation.*.*(..))", returning = "result")
    @AfterReturning(value = "declareJoinPointerExpression()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + " AfterReturning ends with " + result);
    }

    //4、异常通知：在目标方法出现异常 时会执行的代码，可以访问到异常对象：且可以!!指定在出现特定异常时在执行通知!!,如果是修改为nullPointerException里，只有空指针异常才会执行
//    @AfterThrowing(value = "execution(* Spring4_AOP.aopAnnotation.*.*(..))", throwing = "except")
    @AfterThrowing(value = "declareJoinPointerExpression())", throwing = "except")
    public void afterThrowing(JoinPoint joinPoint, Exception except){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method " + methodName + "  occurs exception " + except);
    }

    /**
     * 5、环绕通知 需要携带 ProceedingJoinPoint 类型的参数.
     * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
    //   @Around("execution(* Spring4_AOP.aopAnnotation.*.*(..))")
    @Around("declareJoinPointerExpression()")
    public Object aroundMethod(ProceedingJoinPoint pjd){

        Object result = null;
        String methodName = pjd.getSignature().getName();

        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            //执行目标方法
            result = pjd.proceed();
            //返回通知
            System.out.println("The method " + methodName + " ends with " + result);
        } catch (Throwable e) {
            //异常通知
            System.out.println("The method " + methodName + " occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends");

        return result;
    }

}
