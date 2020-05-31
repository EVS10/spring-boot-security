package slava.bank.model;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(nullable = false)
    private BigDecimal balance;

    public Client(String firstName, String lastName, String username, String password, Role role, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
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
        return 1;
    }

    public Role getRole() {
        return role;
    }

    public BigDecimal getBalance() {
        return balance;
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
                ", role='" + role + '\'' +
                ", balance=" + balance +
                '}';
    }
}
