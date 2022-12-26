package Users;

import org.example.RequestType;

public class Person extends User {

    public Person(UserType userType, String username) {
        allowedRequests.add(RequestType.INLOCUIRE_BULETIN);
        allowedRequests.add(RequestType.INLOCUIRE_CARNET_DE_SOFER);
        this.userType = userType;
        this.username = username;
    }

}
