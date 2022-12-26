package org.example;

import java.util.Date;

public class Request implements Comparable<Request> {
    private String username; //TODO posibil sa nu fie nevoie de el

    private RequestType type;
    private Date dateAndTime;
    private Integer priority;

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
        } else { // equal priorities
            return this.getDateAndTime().compareTo(o.getDateAndTime());
        }
    }

    public Request(String username, String requestType, Date dateAndTime, Integer priority) {
        this.username = username;
        this.type = RequestType.valueOfInput(requestType);
        this.setDateAndTime(dateAndTime);
        this.setPriority(priority);
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

    //    public SendToOfficeQueue() {
//    }



}
