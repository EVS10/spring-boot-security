package rc.bootsecurity.model;

public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
