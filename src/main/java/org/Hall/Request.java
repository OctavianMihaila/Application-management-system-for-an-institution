package org.Hall;

import Users.User;

public class Request implements Comparable<Request> {
    private String username; //TODO posibil sa nu fie nevoie de el

    private RequestType type;
    private String dateAndTime;
    private Integer priority;

    public String getUsername() {
        return username;
    }

    public RequestType getType() {
        return type;
    }

    @Override
    public int compareTo(Request o) {
        return 0;
    }

    public Request(String username, String requestType, String dateAndTime, Integer priority) {
        this.username = username;
        this.type = RequestType.valueOfInput(requestType);
        this.dateAndTime = dateAndTime;
        this.priority = priority;
    }

    //    public SendToOfficeQueue() {
//    }



}
