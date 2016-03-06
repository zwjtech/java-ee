package Spring4_AOP.aopAnnotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author liuChangWen on 2016/3/6.
 */
@Order(1)
@Aspect
@Component
public class ValidationAspect {
    @Before("execution(public int Spring4_AOP.aopAnnotation.*.*(int ,int))")
    public void validateArgs(JoinPoint joinPoint){
        System.out.println("-->validate:" + Arrays.asList(joinPoint.getArgs()));
    }
}
