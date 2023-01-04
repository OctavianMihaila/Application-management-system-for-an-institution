package Utils;

public class UserCanNotParticipateInEventException extends Exception {

    public UserCanNotParticipateInEventException(String username, String userType, String eventType) {
        super(username + ", " + userType + ", can't participate in an event of type: " + eventType);
    }
}
