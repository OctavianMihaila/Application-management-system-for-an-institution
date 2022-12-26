package Users;

import org.Hall.RequestType;

public class LegalEntity extends User {

    private String representative;
    public LegalEntity(UserType userType, String username, String representative) {
        allowedRequests.add(RequestType.CREARE_ACT_CONSTITUTIV);
        allowedRequests.add(RequestType.REINNOIRE_AUTORIZATIE);
        this.userType = userType;
        this.username = username;
        this.representative  = representative;
    }

    public String requestAsUser() {
        return String.format(" reprezentant legal al companiei %s,", getRepresentative());
    }

    public String getRepresentative() {
        return representative;
    }

    @Override
    public String getRepresentant() {
        return this.representative;
    }
}
