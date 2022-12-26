package org.example;

import Users.*;
import Utils.Parser;
import Utils.WriteFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Users.UserType.*;

public class ManagementPrimarie {
    private static Map<String, Office> offices;
    private static List<User> users;
    public static class IncorrectOfficeTypeException extends Exception {
        public IncorrectOfficeTypeException(String providedType) {
            super("Provided office type is wrong: " + providedType);
        }
    }

    private static Office getOffice(String officeType) throws IncorrectOfficeTypeException {
        UserType type = valueOfInput(officeType);
        switch (type) {
            case PERSOANA:
                offices.putIfAbsent(Person.class.getName(), new Office(Person.class));
                return offices.get(Person.class.getName());

            case ANGAJAT:
                offices.putIfAbsent(Employee.class.getName(), new Office(Employee.class));
                return offices.get(Employee.class.getName());

            case PENSIONAR:
                offices.putIfAbsent(Retired.class.getName(), new Office(Retired.class));
                return offices.get(Retired.class.getName());

            case ELEV:
                offices.putIfAbsent(Student.class.getName(), new Office(Student.class));
                return offices.get(Student.class.getName());

            case ENTITATE_JURIDICA:
                offices.putIfAbsent(LegalEntity.class.getName(), new Office(LegalEntity.class));
                return offices.get(LegalEntity.class.getName());

            default:
                throw new IncorrectOfficeTypeException(officeType);
        }
    }

    public static User findUser(String username) {
        for (User u: users) {
            if (u.getUsername().equals(username) ||
                    (u instanceof LegalEntity && ((LegalEntity)u).getRepresentative().equals(username))) {
                return u;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, ParseException {
        // Cleaning data from the previous running.
        //Arrays.stream(new File("src/main/resources/output").listFiles()).forEach(File::delete);

        SimpleDateFormat formater = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String inputFile = args[0];
        String username;
        String officeType;
        String servantName;
        Date dateAndTIme;
        User user = null;
        Office office = null;
        users = new ArrayList<>();
        offices = new HashMap<>();

        try {
            String line;
            BufferedReader reader =
                    new BufferedReader(new FileReader("src/main/resources/input/" + inputFile));

            while ((line = reader.readLine()) != null) {
                line += ";";
                Parser parser = new Parser(line);
                String request = line.substring(0, line.indexOf(";"));

                switch (request) {
                    case "adauga_functionar":
                        String type = parser.getFirstAttribute();
                        String civilServantName = parser.getSecondAttribute();
                        try {
                            office = getOffice(type);
                            office.addServant(new CivilServant(civilServantName));
                        } catch (IncorrectOfficeTypeException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "adauga_utilizator":
                        try {
                            users.add(User.createNewUser(line));
                        } catch (User.IncorrectUserTypeException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "cerere_noua":
                        username = parser.getFirstAttribute();
                        String requestType = parser.getSecondAttribute();
                        Date dateAndTime = formater.parse(parser.getThirdAttribute());
                        Integer priority = Integer.parseInt(parser.getFourthAttribute());

                        user = findUser(username);
                        if (user != null) {
                            RequestType requestTypeValue = RequestType.valueOfInput(requestType);
                            if (user.getAllowedRequests().contains(requestTypeValue)) {
                                Request newRequest = new Request(username, requestType, dateAndTime, priority);
                                user.addPendingRequest(newRequest);
                                office = null;
                                try {
                                    office = getOffice(user.getUserType().getValue());
                                    if (office != null) {
                                        office.addRequest(newRequest);
                                    } else {
                                        System.out.println("office not found");
                                    }
                                } catch (IncorrectOfficeTypeException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                List<String> content = new ArrayList<>();
                                content.add(String.format("Utilizatorul de tip %s nu poate inainta o cerere de tip %s\n", user.getUserType().getValue(),
                                        requestType));
                                WriteFile.writeWithAppend(content, inputFile);
                            }

                        } else {
                            System.out.println("user not found");
                        }



                        break;

                    case "retrage_cerere":
                        username = parser.getFirstAttribute();
                        dateAndTime = formater.parse(parser.getSecondAttribute());
                        user = findUser(username);
                        user.deleteRequest(dateAndTime);
                        try {
                            office = getOffice(user.getUserType().getValue());
                            TreeSet<Request> requestsToSearch = office.getRequests();

                            for (Request r: requestsToSearch) {
                                //System.out.println(r.getDateAndTime().toString() + "///" + requestDateAndTime);
                                if (r.getDateAndTime().equals(dateAndTime)) {
                                    //System.out.println("FOUND");
                                    requestsToSearch.remove(r);
                                    break;
                                }
                            }
                        } catch (IncorrectOfficeTypeException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "rezolva_cerere":
                        officeType = parser.getFirstAttribute();
                        servantName = parser.getSecondAttribute();
                        try {
                            office = getOffice(officeType);
                            Request requestToSolve = (Request) office.getRequests().pollFirst();
                            CivilServant servant = office.findServant(servantName);
                            servant.addSolvedRequest(requestToSolve);
                            user = findUser(requestToSolve.getUsername());
                            // Move request from pending to finished.
                            user.addFinishedRequest(requestToSolve);
                            user.deleteRequest(requestToSolve.getDateAndTime());

                        } catch (IncorrectOfficeTypeException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "afiseaza_cereri_in_asteptare":
                        username = parser.getFirstAttribute();
                        user = findUser(username);

                        WriteFile.writeWithAppend(user.getRequestsAsStrings(true), inputFile);
                        break;

                    case "afiseaza_cereri_finalizate":
                        username = parser.getFirstAttribute();
                        user = findUser(username);

                        WriteFile.writeWithAppend(user.getRequestsAsStrings(false), inputFile);
                        break;

                    case "afiseaza_cereri":
                        try {
                            officeType = parser.getFirstAttribute();
                            office = getOffice(officeType);
                            if (office != null) {
                                TreeSet<Request> requests =  office.getRequests();
                                //requests.pollFirst();
                                Request r;
                                List<String> content = new ArrayList<>();
                                content.add(officeType + " - cereri in birou:\n");

                                Iterator<Request> iterator = requests.iterator();
                                while (iterator.hasNext()) {
                                    r = iterator.next();
                                    user = findUser(r.getUsername());
                                    content.add(r.getPriority() + " - " + formater.format(r.getDateAndTime()) + " - " +
                                            user.writeRequest(r.getType().getValue()) + "\n");
                                }

                                WriteFile.writeWithAppend(content, inputFile);

                            } else {
                                System.out.println("Office does not exist");
                            }
                        } catch (IncorrectOfficeTypeException e) {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        throw new ParseException("Invalid request", 0);
                }
            }

            Iterator<Map.Entry<String, Office>> iterator = offices.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Office> entry = iterator.next();
                List<CivilServant> servants = entry.getValue().getServants();

                for (CivilServant cs: servants) {
                    if (!cs.getSolvedRequests().isEmpty()) {
                        //String testNum = inputFile.substring(0, inputFile.indexOf("_"));
                        String outputFile = "functionar_" + cs.getName() + ".txt";
                        WriteFile.writeWithAppend(cs.getLogs(), outputFile);
                    }
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        for (User u: users) {
//            System.out.println(u.getUsername());
//        }
//
//        System.out.println("--------");
//
//        Iterator<Map.Entry<String, Office>> iterator = offices.entrySet().iterator();
//
//        while (iterator.hasNext()) {
//            System.out.println("////");
//            Map.Entry<String, Office> entry = iterator.next();
//
//            PriorityQueue<Request> requests = entry.getValue().getRequests();
//            for (Request r: requests) {
//                System.out.println(r.getType() + "##" + r.getUsername());
//            }
//            System.out.println();
//        }

    }
}