package Users;

import org.example.RequestType;

public class Employee extends User {

    private final String company;

    public Employee(UserType userType, String username, String company) {
        allowedRequests.add(RequestType.ID_REPLACEMENT);
        allowedRequests.add(RequestType.DRIVER_LICENSE_REPLACEMENT);
        allowedRequests.add(RequestType.SALARY_INCOME_REGISTRATION);
        this.userType = userType;
        this.username = username;
        this.company = company;
    }

    @Override
    public String requestAsUser() {
        return String.format(" angajat la compania %s,", company);
    }
}
