package Users;

import org.example.Request;
import org.example.RequestType;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class User {

    private final List<Request> pendingRequests = new ArrayList<>();
    private final List<Request> finishedRequests = new ArrayList<>();
    protected String username;
    protected UserType userType;
    protected List<RequestType> allowedRequests = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getRepresentant() {
        return username;
    }

    public List<RequestType> getAllowedRequests() {
        return allowedRequests;
    }

    /**
     * Generates a list of strings containing the requests. If pendingOrFinished is true,
     * then the list will contain pending requests. Otherwise, finished requests.
     *
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

        for (Request r : requests) {
            content.add(formater.format(r.getDateAndTime()) + " - " +
                    this.writeRequest(r.getType().getValue()) + "\n");
        }

        return content;
    }

    /**
     * Used to generate custom request text for each type of user.
     */
    public String requestAsUser() {
        return "";
    }

    public String writeRequest(String requestType) {
        return String.format("Subsemnatul %s,%s va rog sa-mi aprobati urmatoarea solicitare: %s",
                username, requestAsUser(), requestType);
    }

    public void addPendingRequest(Request pendingRequest) {
        this.pendingRequests.add(pendingRequest);
    }

    public void addFinishedRequest(Request finishedRequest) {
        this.finishedRequests.add(finishedRequest);
    }

    /**
     * Removes a request from a user's list of pending requests.
     *
     * @param dateAndTime
     */
    public void deleteRequest(Date dateAndTime) {
        for (Request r : this.pendingRequests) {
            if (r.getDateAndTime().equals(dateAndTime)) {
                this.pendingRequests.remove(r);
                return;
            }
        }
    }
}
