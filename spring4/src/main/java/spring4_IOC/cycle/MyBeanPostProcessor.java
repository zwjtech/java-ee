package Spring4_IOC.cycle;

import Spring4_IOC.bean.CarCycle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * MyBeanPostProcessor
 * Bean 后置处理器允许在调用初始化方法前后对 Bean 进行额外的处理.
 Bean 后置处理器对 IOC 容器里的所有 Bean 实例逐一处理, 而非单一实例. 其典型应用是: 检查 Bean 属性的正确性或根据特定的标准更改 Bean 的属性.
 *
 * @author Lcw 2015/11/12
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessBeforeInitialization..." + o+","+s);

        //过滤
        if ("car".equals(o)){

        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessAfterInitialization..." + o+","+s);
        CarCycle car = new CarCycle();
        car.setBrand("Ford");
        return car;
    }
}
