package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity present in the office that deals with solving pending requests.
 */
public class CivilServant {

    private final String name;
    private final List<Request> solvedRequests;

    public CivilServant(String name) {
        this.name = name;
        this.solvedRequests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Request> getSolvedRequests() {
        return solvedRequests;
    }

    public void addSolvedRequest(Request request) {
        this.solvedRequests.add(request);
    }

    /**
     * @return Returns the list of requests resolved by a civil servant.
     */
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

        for (Request r : solvedRequests) {
            logs.add(formater.format(r.getDateAndTime()) + " - " + r.getUsername() + "\r\n");
        }

        return logs;
    }
}
