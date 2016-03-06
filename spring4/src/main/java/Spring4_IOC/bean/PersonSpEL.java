package Spring4_IOC.bean;

/**
 * @author Administrator on 2016/3/6.
 */
public class PersonSpEL {
    private Car car;
    private String name;

    //根据car的price确定Info
    private String info;
    //引用address bean的city的属性
    private String city;

    @Override
    public String toString() {
        return "PersonSpEL{" +
                "city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", info=" + info +
                ", car=" + car +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public PersonSpEL() {

    }
}
