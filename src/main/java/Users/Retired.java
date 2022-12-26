package Users;

import org.example.RequestType;

public class Retired extends User {

    public Retired(UserType userType, String username) {
        allowedRequests.add(RequestType.INLOCUIRE_BULETIN);
        allowedRequests.add(RequestType.INLOCUIRE_CARNET_DE_SOFER);
        allowedRequests.add(RequestType.INREGISTRARE_CUPOANE_DE_PENSIE);
        this.userType = userType;
        this.username = username;
    }
}
