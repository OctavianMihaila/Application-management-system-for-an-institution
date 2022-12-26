package org.Hall;

public enum RequestType {
    INLOCUIRE_BULETIN("inlocuire buletin"),
    INREGISTRARE_VENIT_SALARIAL("inregistrare venit salarial"),
    INLOCUIRE_CARNET_DE_SOFER("inlocuire carnet de sofer"),
    INLOCUIRE_CARNET_DE_ELEV("inlocuire carnet de elev"),
    CREARE_ACT_CONSTITUTIV("creare act constituti"),
    REINNOIRE_AUTORIZATIE("reinnoire autorizatie"),
    INREGISTRARE_CUPOANE_DE_PENSIE("inregistrare cupoane de pensie");

    private String value;

    public String getValue() {
        return this.value;
    }

    private RequestType(String value) {
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


}
