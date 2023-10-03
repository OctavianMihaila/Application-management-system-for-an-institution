# Institution Request Management System

## Description

This is an application management system designed for an institution to efficiently handle citizens' requests processed by civil servants. Its primary aim is to eliminate queues for tasks such as registering a business, changing IDs, or registering a student ID.

## Testing

To perform testing, run the program using the files located in `src/main/resources/input` and compare the results with the reference files in `src/main/resources/references` against the program's output in `src/main/resources/output`. Ensure that the output directory exists or create it beforehand for the proper operation of the testing process.

## Structure

The institution request management system is structured around various user types, with each type of user having their designated office. Here's an overview of the system's structure:

- **User:** Users create requests that are subsequently placed in specific offices. Requests are queued based on priority index and creation timestamp. Users have two lists to keep track of their requests: pending and finished. Additionally, there is a list of allowed request types represented by the `RequestType` enum.

- **Office:** Offices are generic and associated with user types. They contain a request queue ordered by priorities, with priority index and creation date as the main criteria. Requests are stored using a `TreeSet` collection, chosen for its ability to maintain order via the `Comparable` interface. Resolving the first request in the queue can be achieved with the `pollFirst` method, which also removes the request from the collection.

- **Civil Servant:** Civil servants are generic entities that can handle various types of requests, making them versatile for different offices. They can resolve requests, transferring them from the office's pending list to the finished list for users.

- **Event:** Users can create requests to organize or participate in city events. Event requests specify the type of event, funding source (self-funded or institutional), maximum participants, registration deadline, and the type of participants allowed. An accounting office manages event requests, approving those within the budget (with an option to add funds to the institution).

## Implementation

### User:

- Users are created using the Factory design pattern, starting from the abstract `User` class, which represents the common aspects of all users.

- Users maintain two lists for request tracking (pending and finished) and have a list of allowed request types (`RequestType` enum).

- Lists are used for displaying requests in the order of creation/completion, and `List` is a suitable collection for this purpose.

- The `requestAsUser` method is overridden by classes that inherit from the `User` base class, providing customization for each user type.

### Office:

- The `Office` class is generic and categorized based on user types.

- It contains a request queue, prioritized by a combination of priority index and request creation date.

- Requests are stored in a `TreeSet` collection, chosen for its ease of maintaining request order using the `Comparable` interface.

- To access and remove the first request needing resolution, the `pollFirst` method is used.

### Civil Servant:

- Civil servants are designed to be generic, capable of handling any type of request from different offices.

- They can resolve requests, moving them from the office's pending list to the finished list for users.

### Event:

- Users create event requests, specifying event type, funding source, maximum participants, registration deadline, and allowed participant types.

- An accounting office manages event requests, approving those within budget constraints, with the option to add funds to the institution.

