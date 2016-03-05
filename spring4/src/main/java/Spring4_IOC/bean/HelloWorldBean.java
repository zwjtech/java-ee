package Spring4_IOC.bean;

/**
 * HelloWorldBean
 *
 * @author Lcw 2015/11/5
 */
public class HelloWorldBean {
    private String user;

    public HelloWorldBean() {
        System.out.println("HelloWorldBean's constructor...");
    }

    public void setUser(String user) {
        System.out.println("setUser:" + user);
        this.user = user;
    }

    public HelloWorldBean(String user) {
        this.user = user;
    }

    public void hello(){
        System.out.println("Hello: " + user);
    }
}
