package org.Hall;

public class Request {

    private enum Type {
        INLOCUIRE_BULETIN,
        INREGISTRARE_VENIT_SALARIAL,
        INLOCUIRE_CARNET_DE_SOFER,
        INLOCUIRE_CARNET_DE_ELEV,
        CREARE_ACT_CONSTITUTIV,
        REINNOIRE_AUTORIZATIE,
        INREGISTRARE_CUPOANE_DE_PENSIE;
    }

    private String name;
    private String dateAndTime;
    private Integer priority;

//    public SendToOfficeQueue() {
//    }


}
