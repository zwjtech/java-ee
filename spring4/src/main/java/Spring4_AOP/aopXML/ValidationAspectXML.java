package Spring4_AOP.aopXML;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

/**
 * ValidationAspectXML
 *
 * @author Lcw 2015/11/13
 */
public class ValidationAspectXML {

    public void validateArgs(JoinPoint joinPoint){
        System.out.println("-->validate:" + Arrays.asList(joinPoint.getArgs()));
    }
}
