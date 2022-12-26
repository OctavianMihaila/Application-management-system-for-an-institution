package Users;

import org.Hall.RequestType;

public class Student extends User {

    private String school;
    public Student(UserType userType, String username, String school) {
        allowedRequests.add(RequestType.INLOCUIRE_BULETIN);
        allowedRequests.add(RequestType.INLOCUIRE_CARNET_DE_ELEV);
        this.userType = userType;
        this.username = username;
        this.school = school;
    }

    public String requestAsUser() {
        return String.format("elev la scoala %s,", school);
    }


}
