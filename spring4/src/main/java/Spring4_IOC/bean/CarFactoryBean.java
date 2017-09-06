package Spring4_IOC.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * CarFactoryBean:自定义的factoryBean需要实现FactoryBean
 *
 * @author Lcw 2015/11/12
 */
public class CarFactoryBean implements FactoryBean<Car> {
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public CarFactoryBean() {
    }

    //下面是三个实现方法
    //返回bean的对象
    public Car getObject() throws Exception {
        return new Car("Audi" , 500000);
    }

    //返回bean的类型Class<?>  中?表示一个未知的类
    public Class<?> getObjectType() {
        return Car.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
