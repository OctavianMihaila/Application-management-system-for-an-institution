package org.example;

import Users.*;
import Utils.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Users.UserType.valueOfInput;

public class ManagementPrimarie {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    private static Map<String, Office> offices;
    private static List<User> users;
    private static List<Event> events;
    private static String inputFile;
    private static Integer eventBudget;

    /**
     * If the office doesn't exist and the office type is valid, it creates and
     * add the new office to the offices map, then return the newly created object.
     *
     * @param officeType
     * @return The office with a specific name in the Map containing offices.
     * @throws IncorrectOfficeTypeException
     */
    private static Office getOffice(String officeType) throws IncorrectOfficeTypeException {
        UserType type = valueOfInput(officeType);

        switch (type) {
            case PERSON:
                offices.putIfAbsent(Person.class.getName(), new Office(Person.class));
                return offices.get(Person.class.getName());

            case EMPLOYEE:
                offices.putIfAbsent(Employee.class.getName(), new Office(Employee.class));
                return offices.get(Employee.class.getName());

            case RETIRED:
                offices.putIfAbsent(Retired.class.getName(), new Office(Retired.class));
                return offices.get(Retired.class.getName());

            case STUDENT:
                offices.putIfAbsent(Student.class.getName(), new Office(Student.class));
                return offices.get(Student.class.getName());

            case LEGAL_ENTITY:
                offices.putIfAbsent(LegalEntity.class.getName(), new Office(LegalEntity.class));
                return offices.get(LegalEntity.class.getName());

            case ACCOUNTANCY:
                offices.putIfAbsent("contabilitate", new Office(null));
                return offices.get("contabilitate");

            default:
                throw new IncorrectOfficeTypeException(officeType);
        }
    }

    /**
     * @param username
     * @return The user with a specific name in the users list.
     */
    public static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    /**
     * @param title
     * @return The event with a specific title
     */
    public static Event findEvent(String title) throws EventNotFoundException {
        for (Event event: events) {
            if (event.getTitle().equals(title)) {
                return event;
            }
        }

        throw new EventNotFoundException(title);
    }

    /**
     * Adds a new civil servant to its specific office.
     *
     * @param parser
     */
    private static void addCivilServant(Parser parser) {
        String type = parser.getFirstAttribute();
        String civilServantName = parser.getSecondAttribute();
        try {
            Office office = getOffice(type);
            office.addServant(new CivilServant(civilServantName));
        } catch (IncorrectOfficeTypeException e) {
            e.printStackTrace();
        }
    }

    private static void addUser(String line) {
        try {
            users.add(UserFactory.createNewUser(line));
        } catch (IncorrectUserTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a custom request based on the received type. For EVENT_ORGANIZATION and EVENT_REGISTRATION
     * an EventRequest object is created. Otherwise a normal Request object is created.
     * @param requestTypeValue
     * @param parser
     * @param username
     * @param dateAndTime
     * @param priority
     * @return
     * @throws ParseException
     */
    private static Request generateRequestByType(RequestType requestTypeValue, Parser parser,
                                                 String username, Date dateAndTime, Integer priority)
                                                throws ParseException {
        Request newRequest = null;

        if (requestTypeValue.equals(RequestType.EVENT_ORGANIZATION)) {
            Map<String, String> eventAttributes = parser.getEventAttributes();
            newRequest = new EventRequest(username, requestTypeValue.getValue(), dateAndTime, priority,
                    eventAttributes.get("arg1"), // title
                    Boolean.parseBoolean(eventAttributes.get("arg2")), // financedByMunicipality
                    Integer.parseInt(eventAttributes.get("arg3")), // financeAmount
                    DATE_FORMATTER.parse(eventAttributes.get("arg4")), // scheduledTime
                    DATE_FORMATTER.parse(eventAttributes.get("arg5")), // registrationDeadline
                    Integer.parseInt(eventAttributes.get("arg6"))); // maxAllowedParticipants
        } else if (requestTypeValue.equals(RequestType.EVENT_REGISTRATION)) {
            Map<String, String> eventAttributes = parser.getEventAttributes();
            String eventName = eventAttributes.get("arg1");
            newRequest = new EventRequest(username, requestTypeValue.getValue(), dateAndTime, priority, eventName);
        } else {
            newRequest = new Request(username, requestTypeValue.getValue(), dateAndTime, priority);
        }

        return newRequest;
    }

    /**
     * Performs adding a new request to a specific office's pending queue, based on the
     * user type. Also checks if the user is allowed to do that type of request and if yes,
     * then adds the request tot the user's list of pending requests.
     *
     * @param parser
     * @throws ParseException
     * @throws IncorrectRequestTypeException
     */
    private static void createNewRequest(Parser parser) throws ParseException, IncorrectRequestTypeException {
        String username = parser.getFirstAttribute();
        User user = findUser(username);
        Request newRequest = null;

        if (user != null) {
            String requestType = parser.getSecondAttribute();
            RequestType requestTypeValue = RequestType.valueOfInput(requestType);

            if (user.getAllowedRequests().contains(requestTypeValue)) {
                Integer priority = Integer.parseInt(parser.getFourthAttribute());
                Date dateAndTime = DATE_FORMATTER.parse(parser.getThirdAttribute());

                newRequest = generateRequestByType(requestTypeValue, parser, username, dateAndTime, priority);
                user.addPendingRequest(newRequest);
                try {
                    Office office = null;
                    if (requestTypeValue.equals(RequestType.EVENT_ORGANIZATION)) {
                        office = getOffice("contabilitate");
                    } else {
                        office = getOffice(user.getUserType().getValue());
                    }
                    office.addRequest(newRequest);
                } catch (IncorrectOfficeTypeException e) {
                    e.printStackTrace();
                }
            } else {
                List<String> content = new ArrayList<>();

                content.add(String.format("Utilizatorul de tip %s nu poate inainta o cerere de tip %s\n",
                        user.getUserType().getValue(), requestType));
                WriteFile.writeWithAppend(content, inputFile);
                throw new IncorrectRequestTypeException(requestTypeValue.getValue());
            }
        } else {
            System.out.println("user not found");
        }
    }

    /**
     * Performs the process of withdrawing a request from an office.
     * Removes the request from both the user and the office pending queue.
     *
     * @param parser
     * @throws ParseException
     */
    private static void withdrawRequest(Parser parser) throws ParseException {
        String username = parser.getFirstAttribute();
        User user = findUser(username);
        Date dateAndTime = DATE_FORMATTER.parse(parser.getSecondAttribute());

        user.deleteRequest(dateAndTime);
        try {
            Office office = getOffice(user.getUserType().getValue());
            office.removeRequest(dateAndTime);
        } catch (IncorrectOfficeTypeException e) {
            e.printStackTrace();
        }
    }

    private static void handleEventOrganization(Request requestToSolve) {
        Integer eventCost = ((EventRequest)requestToSolve).getFinanceAmonut();

        if (((EventRequest)requestToSolve).getFinancedByMunicipality() && eventCost < eventBudget) {
            eventBudget -= eventCost;
        } else if (((EventRequest)requestToSolve).getFinancedByMunicipality()) {
            WriteFile.writeWithAppend(Collections.singletonList("Insufficient funds to sponsor this event: " +
                    ((EventRequest) requestToSolve).getTitle()), inputFile);
        }

        events.add(new Event(findUser(requestToSolve.getUsername()).getUserType(),
                ((EventRequest) requestToSolve).getTitle(),
                ((EventRequest) requestToSolve).getFinancedByMunicipality(),
                ((EventRequest) requestToSolve).getFinanceAmonut(),
                ((EventRequest) requestToSolve).getScheduledTime(),
                ((EventRequest) requestToSolve).getRegistrationDeadline(),
                ((EventRequest) requestToSolve).getMaxAllowedParticipants()));
    }

    private static void handleEventRegistration(Request requestToSolve)
            throws UserCanNotParticipateInEventException {
        Event event = null;

        try {
            event = findEvent(((EventRequest)requestToSolve).getTitle());
        } catch (EventNotFoundException e) {
            e.printStackTrace();
        }

        // Checking if the user is allowed to take part in this kind of event.
        if (event.getParticipantsType().equals(findUser(requestToSolve.getUsername()).getUserType())) {
            if (event.getMaxAllowedParticipants() - event.getRegisteredParticipants() > 0 &&
                    event.getRegistrationDeadline().compareTo(requestToSolve.getDateAndTime()) > 0) {
                event.registerNewParticipant(findUser(requestToSolve.getUsername()));
            } else if (event.getMaxAllowedParticipants() - event.getRegisteredParticipants() <= 0 ) {
                WriteFile.writeWithAppend(Collections.singletonList(
                        "The event can no longer accept participants (full capacity) " +
                                ((EventRequest) requestToSolve).getTitle()), inputFile);
            } else if (event.getRegistrationDeadline().compareTo(requestToSolve.getDateAndTime()) <= 0) {
                WriteFile.writeWithAppend(Collections.singletonList("Deadline for registration expired" +
                        ((EventRequest) requestToSolve).getTitle()), inputFile);
            }
        } else {
            throw new UserCanNotParticipateInEventException(requestToSolve.getUsername(),
                    findUser(requestToSolve.getUsername()).getUserType().getValue(),
                    (((UserType)event.getParticipantsType()).getValue()));
        }
    }

    /**
     * Performs a solve for a request. The request is removed from the office's
     * pending queue, then moved from the user's pending to finished queue.
     * Also a log is added to the servant that solved that request.
     *
     * @param parser
     */
    private static void solveRequest(Parser parser) {
        try {
            String officeType = parser.getFirstAttribute();
            Office office = getOffice(officeType);
            Request requestToSolve = (Request) office.getRequests().pollFirst();

            if (requestToSolve.getType().equals(RequestType.EVENT_ORGANIZATION)) {
                handleEventOrganization(requestToSolve);
            }

            if (requestToSolve.getType().equals(RequestType.EVENT_REGISTRATION)) {
                try {
                    handleEventRegistration(requestToSolve);
                } catch (UserCanNotParticipateInEventException e) {
                    e.printStackTrace();
                }
            }

            String servantName = parser.getSecondAttribute();

            try {
                CivilServant servant = office.findServant(servantName);
                servant.addSolvedRequest(requestToSolve);
                User user = findUser(requestToSolve.getUsername());

                // Move request from pending to finished.
                user.addFinishedRequest(requestToSolve);
                user.deleteRequest(requestToSolve.getDateAndTime());
            } catch (ServantNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IncorrectOfficeTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a list of requests based on the value of the pending parameter.
     *
     * @param parser
     * @param pending If true it prints the pending list, otherwise the finished list is printed.
     */
    private static void printUserRequests(Parser parser, boolean pending) {
        String username = parser.getFirstAttribute();
        User user = findUser(username);

        WriteFile.writeWithAppend(user.getRequestsAsStrings(pending), inputFile);
    }

    /**
     * Prints the requests in the pending queue of an office.
     *
     * @param parser
     */
    private static void printRequests(Parser parser) {
        try {
            String officeType = parser.getFirstAttribute();
            Office office = getOffice(officeType);

            if (office != null) {
                TreeSet<Request> requests = office.getRequests();
                Request r = null;
                List<String> content = new ArrayList<>();
                Iterator<Request> iterator = requests.iterator();

                content.add(officeType + " - cereri in birou:\n");
                while (iterator.hasNext()) {
                    r = iterator.next();
                    User user = findUser(r.getUsername());
                    content.add(r.getPriority() + " - " + DATE_FORMATTER.format(r.getDateAndTime()) +
                            " - " + user.writeRequest(r.getType().getValue()) + "\n");
                }

                WriteFile.writeWithAppend(content, inputFile);

            } else {
                System.out.println("Office does not exist");
            }
        } catch (IncorrectOfficeTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs the writing of the logs for every civil servant.
     * This is done when the entire process has finished.
     */
    private static void printCivilServantsLogs() {
        Iterator<Map.Entry<String, Office>> iterator = offices.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Office> entry = iterator.next();
            List<CivilServant> servants = entry.getValue().getServants();

            for (CivilServant cs : servants) {
                if (!cs.getSolvedRequests().isEmpty()) {
                    String outputFile = "functionar_" + cs.getName() + ".txt";
                    WriteFile.writeWithAppend(cs.getLogs(), outputFile);
                }
            }
        }
    }

    /**
     * Prints all the registered participants to an event.
     * @param parser
     */
    private static void printParticipants(Parser parser) {
        List<String> participants = new ArrayList<>();
        String title = parser.getFirstAttribute();
        Event eventToRegisterFor = null;

        try {
            eventToRegisterFor = findEvent(title);
        } catch (EventNotFoundException e) {
            e.printStackTrace();
        }

        participants.add("Participanti la evenimentul: " + title + "\n"); // header
        eventToRegisterFor.getRegisteredUsers().
                forEach(user -> {
                    participants.add(((User) user).getUsername() + "\n");
                });
        if (participants.isEmpty()) {
            WriteFile.writeWithAppend(Collections.
                    singletonList("No one is registered to this event"), inputFile);
        } else {
            WriteFile.writeWithAppend(participants, inputFile);
        }
    }

    /**
     * Prints all the registered events.
     */
    private static void printEvents() {
        List<String> content = new ArrayList<>();

        for (Event event: events) {
            content.add("Title: " + event.getTitle() + " | Financed by municipality: " +
                    event.getFinancedByMunicipality() + " | Max allowed participants: " +
                    event.getMaxAllowedParticipants() + " | Scheduled time: "
                    + event.getScheduledTime() + "\n");
        }
        WriteFile.writeWithAppend(content, inputFile);
    }

    private static void init(String[] args) {
        inputFile = args[0];
        users = new ArrayList<>();
        offices = new HashMap<>();
        events = new ArrayList<>();
        eventBudget = 0;
    }

    public static void main(String[] args) {
        init(args);
        BufferedReader reader = null;
        FileReader fileReader = null;

        try {
            String line = null;
            fileReader = new FileReader("src/main/resources/input/" + inputFile);
            reader = new BufferedReader(fileReader);

            while ((line = reader.readLine()) != null) {
                // pipe is specific for event request. No need for semicolon at the end of the line in this case.
                if(!line.contains("|")) {
                    line += ";";
                }
                Parser parser = new Parser(line);
                String request = line.substring(0, line.indexOf(";"));

                switch (request) {
                    case "adauga_functionar":
                        addCivilServant(parser);
                        break;

                    case "adauga_utilizator":
                        addUser(line);
                        break;

                    case "cerere_noua":
                        try {
                            createNewRequest(parser);
                        } catch (IncorrectRequestTypeException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "retrage_cerere":
                        withdrawRequest(parser);
                        break;

                    case "rezolva_cerere":
                        solveRequest(parser);
                        break;

                    case "add_founds_to_event_budget":
                        eventBudget += Integer.parseInt(parser.getFirstAttribute());
                        break;

                    case "afiseaza_cereri_in_asteptare":
                        printUserRequests(parser, true);
                        break;

                    case "afiseaza_cereri_finalizate":
                        printUserRequests(parser, false);
                        break;

                    case "afiseaza_cereri":
                        printRequests(parser);
                        break;

                    case "afiseaza_participanti":
                        printParticipants(parser);
                        break;

                    case "afiseaza_evenimente":
                        printEvents();
                        break;

                    default:
                        throw new ParseException("Invalid request", 0);
                }
            }

            printCivilServantsLogs();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}