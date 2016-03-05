package Spring4_IOC.bean;


import java.util.List;

/**
 * Person1
 *
 * @author Lcw 2015/11/10
 */
public class PersonCarList {
    private String name;
    private int age;
    private List<Car> cars;

    public PersonCarList() {
    }

    @Override
    public String toString() {
        return "PersonCarList{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + cars +
                '}';
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
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
