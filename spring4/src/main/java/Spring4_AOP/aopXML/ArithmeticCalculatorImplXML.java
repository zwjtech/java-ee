package Spring4_AOP.aopXML;

import Spring4_AOP.aopAnnotation.ArithmeticCalculator;

/**
 * ArithmeticCalculatorImplXML
 *
 * @author Lcw 2015/11/12
 */
public class ArithmeticCalculatorImplXML implements ArithmeticCalculator {
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
