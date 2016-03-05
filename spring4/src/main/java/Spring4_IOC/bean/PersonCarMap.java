package Spring4_IOC.bean;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator on 2016/3/5.
 */
public class PersonCarMap {
    private String name;
    private int age;
    private Map<String, Car> cars;

    @Override
    public String toString() {
        return "PersonCarList{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + cars +
                '}';
    }

    public PersonCarMap() {
    }

    public Map<String, Car> getCars() {
        return cars;
    }

    public void setCars(Map<String, Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
