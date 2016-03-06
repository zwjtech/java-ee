package Spring4_IOC.bean.factory;

import Spring4_IOC.bean.Car;

import java.util.HashMap;
import java.util.Map;

/**
 * StaticCarFactory：静态工厂方法：直接调用某一个类的静态方法就可以返回Bean实例
 *
 * @author Lcw 2015/11/12
 */
public class StaticCarFactory {
    private static Map<String , Car> cars = new HashMap<String, Car>();

    static {
        cars.put("auti",new Car("audi", 30000));
        cars.put("ford",new Car("ford", 40000));
    }

    //静态工厂方法: 注意：要在配置文件XML里配置Car而不是StaticCarFactory
    public static Car getCar(String name) {
        return cars.get(name);
    }

}
