package Users;

import Utils.Parser;
import org.Hall.*;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class User {

    protected String username;
    protected UserType userType;
    private List<Request> pendingRequests = new ArrayList<>();

    private List<Request> finishedRequests = new ArrayList<>();
    protected List<RequestType> allowedRequests = new ArrayList<>();

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

    public void addFinishedRequest(Request finishedRequest) {
        this.finishedRequests.add(finishedRequest);
    }

    public String getRepresentant() {
        return username;
    }

    /**
     * Generates a list of strings containing the requests. If pendingOrFinished is true,
     * then the list will contain pending requests. Otherwise, finished requests.
     * @param pendingOrFinished
     * @return
     */
    public List<String> getRequestsAsStrings(Boolean pendingOrFinished) {
        List<String> content = new ArrayList<>();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        List<Request> requests;

        if (pendingOrFinished == true) {
            content.add(getRepresentant() + " - cereri in asteptare:" + "\n");
            requests = this.pendingRequests;

        } else {
            content.add(getRepresentant() + " - cereri in finalizate:" + "\n");
            requests = this.finishedRequests;
        }

        Collections.sort(requests, new Comparator<Request>() {
            @Override
            public int compare(Request o1, Request o2) {
                return o1.getDateAndTime().compareTo(o2.getDateAndTime());
            }
        });

        for (Request r: requests) {
            content.add(formater.format(r.getDateAndTime()) + " - " + this.writeRequest(r.getType().getValue()) + "\n");
        }

        return content;
    }

    public List<RequestType> getAllowedRequests() {
        return allowedRequests;
    }

    public void deleteRequest(Date dateAndTime) {
        for (Request r: this.pendingRequests) {
            if (r.getDateAndTime().equals(dateAndTime)) {
                this.pendingRequests.remove(r);
                return;
            }
        }
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
                //String representative = parser.getFourthAttribute();
                return new LegalEntity(userType, userAttribute, username);

            default:
                throw new IncorrectUserTypeException(type);
        }
    }

}
