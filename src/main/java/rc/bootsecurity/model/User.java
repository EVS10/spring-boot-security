package rc.bootsecurity.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;

    private String roles = "";

    private String permissions = "";

    private BigDecimal balance;

    public User(String username, String password, String roles, String permissions, BigDecimal balance) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.balance = balance;
        this.active = 1;
    }

    protected User() { }

    public long getId() {
        return id;
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

    public String getPermissions() {
        return permissions;
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

    public List<String> getPermissionList() {
        if (roles.length() > 0) {
            return Arrays.asList(permissions.split(","));
        }
        return new ArrayList<>();
    }

    public boolean add(BigDecimal sum) {
        balance = balance.add(sum);
        return true;
    }

    public boolean withdraw(BigDecimal sum) {
        balance = balance.subtract(sum);
        return true;
    }

    public boolean transfer(User receiver, BigDecimal sum) {
        balance = balance.subtract(sum);
        receiver.add(sum);
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
