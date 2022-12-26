package Users;

public enum UserType { //TODO rename content to english
    ELEV("elev"),
    PERSOANA("persoana"),
    ANGAJAT("angajat"),
    PENSIONAR("pensionar"),
    ENTITATE_JURIDICA("entitate juridica");

    private String value;

    public String getValue() {
        return this.value;
    }

    private UserType(String value) {
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
}
