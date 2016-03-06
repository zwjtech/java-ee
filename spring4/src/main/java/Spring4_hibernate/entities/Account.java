package Spring4_hibernate.entities;

/**
 * com.changwen.spring.hibernate.entities.Account
 *
 * @author lcw 2015/11/14
 */
public class Account {

    private Integer id;
    private String username;
    private int balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
