package rc.bootsecurity.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;

    private String roles = "";

    private BigDecimal balance;

    public Client(String firstName, String lastName, String username, String password, String roles, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.balance = balance;
        this.active = 1;
    }

    protected Client() { }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    private boolean isNegative(BigDecimal sum) {
        return sum.compareTo(new BigDecimal(0)) < 0;
    }

    private boolean isBalanceLessThanSum(BigDecimal sum) {
        return balance.compareTo(sum) < 0;
    }

    public boolean add(BigDecimal sum) {
        if (isNegative(sum)) {
            return false;
        }
        balance = balance.add(sum);
        return true;
    }

    public boolean withdraw(BigDecimal sum) {
        if (isNegative(sum) || isBalanceLessThanSum(sum)) {
            return false;
        }
        balance = balance.subtract(sum);
        return true;
    }

    public boolean transfer(Client receiver, BigDecimal sum) {
        if (isNegative(sum) || isBalanceLessThanSum(sum)) {
            return false;
        }
        balance = balance.subtract(sum);
        receiver.add(sum);
        return true;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                ", balance=" + balance +
                '}';
    }
}
