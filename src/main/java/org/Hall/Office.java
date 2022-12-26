package org.Hall;

import Users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Office<T> {

    private T citizen;

    private static Office obj;
    private List<CivilServant> servants;
    private PriorityQueue<Request> requests = new PriorityQueue<>();

    public Office(T citizen) {
        this.citizen = citizen;
        this.servants = new ArrayList<>();
    }
    public void setCitizen(T citizen) {
        this.citizen = citizen;
    }

    public T getCitizen() {
        return citizen;
    }

    public PriorityQueue<Request> getRequests() {
        return requests;
    }

    public void addRequest(Request newRequest) {
        requests.add(newRequest);
    }

//    /**
//     * Used for Singlethon pattern to ensure that we have a single office for every type.
//     * @param user
//     * @return
//     */
//    public static Office getInstance() {
//        if (obj == null) {
//            obj = new Office<>();
//        }
//
//        return obj;
//    }

    public void addServant(CivilServant servant) {
        // TO DO: Add exception for cases when a wrong type request is sent to a office.
        this.servants.add(servant);
    }

}
