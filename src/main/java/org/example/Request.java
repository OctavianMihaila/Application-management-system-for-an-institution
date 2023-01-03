package org.example;

import java.util.Date;

/**
 * Users can submit requests to offices.
 * Those requests can be of different types depending on the type of user.
 */
public class Request implements Comparable<Request> {
    private final String username;
    private final RequestType type;
    private Date dateAndTime;
    private Integer priority;

    public Request(String username, String requestType, Date dateAndTime, Integer priority) {
        this.username = username;
        this.type = RequestType.valueOfInput(requestType);
        this.setDateAndTime(dateAndTime);
        this.setPriority(priority);
    }

    public String getUsername() {
        return username;
    }

    public RequestType getType() {
        return type;
    }

    @Override
    public int compareTo(Request o) {
        if (this.getPriority() > o.getPriority()) {
            return -1;
        } else if (this.getPriority() < o.getPriority()) {
            return 1;
        } else { // equal priorities case, now comparing timestamps.
            return this.getDateAndTime().compareTo(o.getDateAndTime());
        }
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

}
