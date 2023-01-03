package org.example;

public enum RequestType {

    ID_REPLACEMENT("inlocuire buletin"),
    SALARY_INCOME_REGISTRATION("inregistrare venit salarial"),
    DRIVER_LICENSE_REPLACEMENT("inlocuire carnet de sofer"),
    STUDENT_CARD_REPLACEMENT("inlocuire carnet de elev"),
    CONSTITUTIVE_ACT_CREATION("creare act constitutiv"),
    AUTHORIZATION_RENEWAL("reinnoire autorizatie"),
    PENSION_COUPONS_REGISTRATION("inregistrare cupoane de pensie");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    public static RequestType valueOfInput(String input) {
        for (RequestType e : values()) {
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
