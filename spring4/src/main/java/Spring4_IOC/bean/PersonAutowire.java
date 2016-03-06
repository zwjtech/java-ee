package Spring4_IOC.bean;


/**
 * PersonAutowire
 *
 * @author Lcw 2015/11/10
 */
public class PersonAutowire {
    private String name;
    private Address address;
    private Car2 car;

    public PersonAutowire() {
    }

    @Override
    public String toString() {
        return "PersonAutowire{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", car=" + car +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Car2 getCar() {
        return car;
    }

    public void setCar(Car2 car) {
        this.car = car;
    }
}
