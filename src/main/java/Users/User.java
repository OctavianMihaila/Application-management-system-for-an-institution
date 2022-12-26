package Users;

import Utils.Parser;
import org.Hall.*;

import java.util.ArrayList;
import java.util.List;

public abstract class User {

    protected String username;
    protected UserType userType;
    private List<Request> pendingRequests = new ArrayList<>();
    List<RequestType> allowedRequests = new ArrayList<>();

    public UserType getUserType() {
        return userType;
    }

    public String requestAsUser() {
        return "";
    }
    public String writeRequest(String requestType) {
        return String.format("Subsemnatul %s,%s va rog sa-mi aprobati urmatoarea solicitare: %s", username, requestAsUser(), requestType);
    } // TO DO: Create a custom exception() and use throws here.

    public String getUsername() {
        return username;
    }

    public List<Request> getPendingRequests() {
        return pendingRequests;
    }

    public void addPendingRequest(Request pendingRequest) {
        this.pendingRequests.add(pendingRequest);
    }

    public static class IncorrectUserTypeException extends Exception {
        public IncorrectUserTypeException(String providedType) {
            super("Provided user type is wrong: " + providedType);
        }
    }

    /**
     * Creates and adds a new User to a received User list based on the input details.
     * @param users
     * @param userDetails
     * @throws IncorrectUserTypeException
     */
    public static User createNewUser(String userDetails) throws IncorrectUserTypeException {

        Parser parser = new Parser(userDetails);
        String type = parser.getFirstAttribute();
        String username = parser.getSecondAttribute();
        String userAttribute = parser.getThirdAttribute();
        UserType userType = UserType.valueOfInput(type);

        switch (userType) {
            case PERSOANA:
                return new Person(userType, username);

            case ANGAJAT:
               return new Employee(userType, username, userAttribute);

            case PENSIONAR:
                return new Retired(userType, username);

            case ELEV:
                return new Student(userType, username, userAttribute);

            case ENTITATE_JURIDICA:
                return new LegalEntity(userType, username, userAttribute);

            default:
                throw new IncorrectUserTypeException(type);
        }
    }

}
