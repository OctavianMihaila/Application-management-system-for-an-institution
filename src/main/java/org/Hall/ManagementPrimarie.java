package org.Hall;

import Users.*;
import Utils.Parser;

import java.io.*;
import java.text.ParseException;
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
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, ParseException {
        // Cleaning data from the previous running.
        Arrays.stream(new File("src/main/resources/output").listFiles()).forEach(File::delete);

        String inputFile = args[0];
        User user = null;
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
                            Office office = getOffice(type);
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
                        String username = parser.getFirstAttribute();
                        String requestType = parser.getSecondAttribute();
                        String dateAndTime = parser.getThirdAttribute();
                        Integer priority = Integer.parseInt(parser.getFourthAttribute());

                        user = findUser(username);
                        if (user != null) {
                            Request newRequest = new Request(username, requestType, dateAndTime, priority);
                            user.addPendingRequest(newRequest);
                            Office office = null;
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
                            System.out.println("user not found");
                        }

                        break;

                    case "retrage_cerere":
                        // TO DO
                        break;

                    case "rezolva_cerere":
                        // TO DO
                        break;

                    case "afiseaza_cereri_in_asteptare":
                        // TO DO
                        break;

                    case "afiseaza_cereri_finalizate":
                        // TO DO
                        break;

                    case "afiseaza_cereri":
                        try {
                            String officeType = parser.getFirstAttribute();
                            Office office = getOffice(officeType);
                            if (office != null) {
                                PriorityQueue<Request> requests =  office.getRequests();
                                for (Request r: requests) {
                                    user = findUser(r.getUsername());
                                    System.out.println(user.writeRequest(r.getType().getValue()));
                                }
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        for (User u: users) {
//            System.out.println(u.getUsername());
//        }
//
//        System.out.println("--------");
//
        Iterator<Map.Entry<String, Office>> iterator = offices.entrySet().iterator();

        while (iterator.hasNext()) {
            System.out.println("////");
            Map.Entry<String, Office> entry = iterator.next();

            PriorityQueue<Request> requests = entry.getValue().getRequests();
            for (Request r: requests) {
                System.out.println(r.getType() + "##" + r.getUsername());
            }
            System.out.println();
        }

    }
}