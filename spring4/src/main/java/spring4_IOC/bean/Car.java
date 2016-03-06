package Spring4_IOC.bean;

/**
 * CarCycle
 *
 * @author Lcw 2015/11/5
 */
public class Car {

    private String company;
    private String brand;

    private int maxSpeed;
    private float price;

    public Car() {
    }


    public Car(String brand, float price) {
        super();
        this.brand = brand;
        this.price = price;
    }

    public Car(String company, String brand, float price) {
        super();
        this.company = company;
        this.brand = brand;
        this.price = price;
    }

/*    public CarCycle(String company, String brand, int maxSpeed) {
        super();
        this.company = company;
        this.brand = brand;
        this.maxSpeed = maxSpeed;
    }*/

    public Car(String company, String brand, int maxSpeed, float price) {
        super();
        this.company = company;
        this.brand = brand;
        this.maxSpeed = maxSpeed;
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarCycle [company=" + company + ", brand=" + brand + ", maxSpeed="
                + maxSpeed + ", price=" + price + "]";
    }
}

