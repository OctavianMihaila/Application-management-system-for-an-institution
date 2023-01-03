package Users;

import org.example.RequestType;

public class Retired extends User {

    public Retired(UserType userType, String username) {
        allowedRequests.add(RequestType.ID_REPLACEMENT);
        allowedRequests.add(RequestType.DRIVER_LICENSE_REPLACEMENT);
        allowedRequests.add(RequestType.PENSION_COUPONS_REGISTRATION);
        this.userType = userType;
        this.username = username;
    }
}
