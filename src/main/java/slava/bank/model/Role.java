package slava.bank.model;

public enum Role {

    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    USER("USER");


    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
