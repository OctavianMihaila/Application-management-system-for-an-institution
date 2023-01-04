package Users;

import org.example.RequestType;

public class Person extends User {

    public Person(UserType userType, String username) {
        allowedRequests.add(RequestType.ID_REPLACEMENT);
        allowedRequests.add(RequestType.DRIVER_LICENSE_REPLACEMENT);
        allowedRequests.add(RequestType.EVENT_ORGANIZATION);
        allowedRequests.add(RequestType.EVENT_REGISTRATION);
        this.userType = userType;
        this.username = username;
    }

}
