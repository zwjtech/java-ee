package Spring4_IOC.bean;

/**
 * CarCycle
 *
 * @author Lcw 2015/11/12
 */
public class CarCycle {
    private String brand;

    public CarCycle() {
        System.out.println("CarCycle's constructor...");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        System.out.println("setBrand...");
        this.brand = brand;
    }

    public void init() {
        System.out.println("init...");
    }

    public void destroy() {
        System.out.println("destroy...");
    }

    @Override
    public String toString() {
        return "CarCycle{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
