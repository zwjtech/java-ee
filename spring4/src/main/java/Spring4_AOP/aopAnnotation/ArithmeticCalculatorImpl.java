package Spring4_AOP.aopAnnotation;

import org.springframework.stereotype.Component;

/**
 * ArithmeticCalculatorImpl
 *
 * @author Lcw 2015/11/12
 */
@Component("arithmeticCalculator")
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {
    public int add(int i, int j) {
        return i + j;
    }

    public int sub(int i, int j) {
        return i - j;
    }

    public int mul(int i, int j) {
        return i * j;
    }

    public int div(int i, int j) {
        return i / j;
    }
}
