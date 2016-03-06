package Spring4_IOC.bean;

/**
 * @author Administrator on 2016/3/5.
 */
public class Car2 {
    private String brand;

    private float price;
    private String company;

    public Car2() {
        System.out.println("CarCycle'2 constructor...");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car2{" +
                "company='" + company + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }
}
