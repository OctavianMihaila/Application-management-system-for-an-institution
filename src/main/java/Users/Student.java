package Users;

import org.example.RequestType;

public class Student extends User {

    private final String school;

    public Student(UserType userType, String username, String school) {
        allowedRequests.add(RequestType.ID_REPLACEMENT);
        allowedRequests.add(RequestType.STUDENT_CARD_REPLACEMENT);
        this.userType = userType;
        this.username = username;
        this.school = school;
    }

    public String requestAsUser() {
        return String.format(" elev la scoala %s,", school);
    }
}
