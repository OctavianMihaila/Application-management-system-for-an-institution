package Users;

import org.example.RequestType;

/**
 * Each legal person is a representative for a company.
 */
public class LegalEntity extends User {

    private final String representative;

    public LegalEntity(UserType userType, String username, String representative) {
        allowedRequests.add(RequestType.CONSTITUTIVE_ACT_CREATION);
        allowedRequests.add(RequestType.AUTHORIZATION_RENEWAL);
        allowedRequests.add(RequestType.EVENT_ORGANIZATION);
        allowedRequests.add(RequestType.EVENT_REGISTRATION);
        this.userType = userType;
        this.username = username;
        this.representative = representative;
    }

    public String requestAsUser() {
        return String.format(" reprezentant legal al companiei %s,", getRepresentative());
    }

    public String getRepresentative() {
        return representative;
    }

    @Override
    public String getUsername() {
        return representative;
    }

    @Override
    public String getRepresentant() {
        return this.representative;
    }
}
