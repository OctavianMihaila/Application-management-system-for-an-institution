package Users;

import org.Hall.RequestType;

public class Employee extends User {

    private String company;

    @Override
    public String requestAsUser() {
        return String.format(" angajat la compania %s,", company);
    }

    public Employee(UserType userType, String username, String company) {
        allowedRequests.add(RequestType.INLOCUIRE_BULETIN);
        allowedRequests.add(RequestType.INLOCUIRE_CARNET_DE_SOFER);
        allowedRequests.add(RequestType.INREGISTRARE_VENIT_SALARIAL);
        this.userType = userType;
        this.username = username;
        this.company = company;
    }
}
