package org.Hall;

import Utils.Parser;

import java.util.ArrayList;
import java.util.List;

public class CivilServant {

    private String name;

    private List<Request> solvedRequests;

    public CivilServant(String name) {
        this.name = name;
        this.solvedRequests = new ArrayList<>();
    }

}
