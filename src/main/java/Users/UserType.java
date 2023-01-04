package Users;

public enum UserType {

    STUDENT("elev"),
    PERSON("persoana"),
    EMPLOYEE("angajat"),
    RETIRED("pensionar"),
    LEGAL_ENTITY("entitate juridica"),
    // Users of this type are never created. It is used just to create a accountancy office easier.
    ACCOUNTANCY("contabilitate");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public static UserType valueOfInput(String input) {
        for (UserType e : values()) {
            if (e.value.equals(input)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }
}
