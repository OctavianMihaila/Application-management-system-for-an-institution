package org.Hall;

import java.util.List;

public class CivilServant<T> {

    private T servant;

    private String name;

    private List<Request> solvedRequests;

    public CivilServant(T servant, String name) {
        this.servant = servant;
        this.name = name;
        this.solvedRequests = null;
    }

//    public static void AddCivilServant() {
//
//    }

}
