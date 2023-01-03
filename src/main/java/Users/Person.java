package Users;

import org.example.RequestType;

public class Person extends User {

    public Person(UserType userType, String username) {
        allowedRequests.add(RequestType.ID_REPLACEMENT);
        allowedRequests.add(RequestType.DRIVER_LICENSE_REPLACEMENT);
        this.userType = userType;
        this.username = username;
    }

}
