package org.example;

import Utils.ServantNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * The hall is structured in several offices, each solving different types of requests.
 *
 * @param <T> Generic parameter to mark which type of user the office specializes in.
 */
public class Office<T> {

    private final T citizen;
    private final List<CivilServant> servants;
    private final TreeSet<Request> requests = new TreeSet<>();

    public Office(T citizen) {
        this.citizen = citizen;
        this.servants = new ArrayList<>();
    }

    public List<CivilServant> getServants() {
        return servants;
    }

    public TreeSet<Request> getRequests() {
        return requests;
    }

    /**
     * Removes a request from the office pending queue.
     *
     * @param dateAndTime
     */
    public void removeRequest(Date dateAndTime) {
        for (Request r : requests) {
            if (r.getDateAndTime().equals(dateAndTime)) {
                requests.remove(r);
                return;
            }
        }
    }

    public void addRequest(Request newRequest) {
        requests.add(newRequest);
    }

    public void addServant(CivilServant servant) {

        this.servants.add(servant);
    }

    /**
     * Looking for a specific servant in the office.
     *
     * @param name
     * @throws ServantNotFoundException
     */
    public CivilServant findServant(String name) throws ServantNotFoundException {
        for (CivilServant cs : servants) {
            if (cs.getName().equals(name)) {
                return cs;
            }
        }

        throw new ServantNotFoundException(name);
    }
}
