# Descripton

        -> This is an application management system for an institution. The main challenge
           is the handling of citizens' requests by civil servants. To avoid queues for
           registering a business, changing IDs or registering a student ID.

# Testing

        -> Testing is done by running the program on the files in src/main/resources/input
           and comparing the files in src/main/resources/references to src/main/resources/output.
        
        -> For proper operation, the output directory must exist/be created beforehand.

# Structure

        -> This is a request management system for an institution. It is structured
           based on some user types, each type of user having assigned their own office.

        -> A request is created by a user and subsequently placed in the specific office.
           It is placed in a waiting queue with the priority index as the main criteria
           and the creation timestamp as the secondary criteria.

        -> The office has the function of solving requests by specific civil servants.

        -> The civil servant is the specific entity of the office, specialized to solve
           a certain type of requests. At the same time, logs are kept with the requests
           resolved by each employee

        -> The institution can also keep a record of the events organized in the city.
           Thus, users can create requests to organize or participate in a specific event.
           At the same time, the institution also has a way of financing these events.

# Implementation

        -> User:

            >>  Users are created starting from the User abstract class which is the common
                part of each user using Factory design pattern. Users have 2 lists in which
                requests are kept (pending and finished) and a list of allowed requests
                (RequestType, an enum that describes all types of requests that can be created).

            >>  The choice of lists to perform these storages is justified by the fact that
                the commands to display the 2 lists propose displaying the requests in the
                order in which they were created/completed, and the list is a good collection
                for that.

            >>  The requestAsUser method is overridden by the classes that inherit the User
                base class and represents the customized part of the request for each user.

        -> Office:

            >>  Office is a generic class based on user types. It contains a request queue
                based on priorities, priority index and request creation date.

            >>  Requests are stored using the TreeSet collection. Its choice over the other
                collections is given by the ease of keeping the order of requests using the
                Comparable interface and implicitly overwriting the compareTo method. Thus,
                to access the first request that needs to be resolved, just use the pollFirst
                method, which also deletes the element from the collection.

        -> Civil Servant:

            >>  The civil servant is generic for all types of offices, assuming the genericity
                of the office. It is designed in such a way that it can resolve any type of
                request, deleting it from the office and transferring it to the own solved list,
                as well as moving it from the pending list to the finished list for the user.

        -> Event:

            >>  The event is created following a request made by a user, who completes the type
                of event he wants to organize (own funding or from the institution), the maximum
                number of participants as well as the date until which registrations can be made
                as well as the type of participants who can take part in the event (for
                simplicity I kept only the type of user who created the event).

            >>  In order to manage requests for organizing events, we have created a special
                accounting office. Thus, requests with funding from the institution are approved
                within the budget (there is also a method of adding funds for the institution).
