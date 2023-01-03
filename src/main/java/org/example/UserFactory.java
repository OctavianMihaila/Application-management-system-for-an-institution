package org.example;

import Users.*;
import Utils.IncorrectUserTypeException;
import Utils.Parser;

/**
 * Used Factory design pattern to create users based on the provided types.
 */
public class UserFactory {

    public static User createNewUser(String userDetails) throws IncorrectUserTypeException {
        Parser parser = new Parser(userDetails);
        String type = parser.getFirstAttribute();
        String username = parser.getSecondAttribute();
        String userAttribute = parser.getThirdAttribute();
        UserType userType = UserType.valueOfInput(type);

        switch (userType) {
            case PERSON:
                return new Person(userType, username);

            case EMPLOYEE:
                return new Employee(userType, username, userAttribute);

            case RETIRED:
                return new Retired(userType, username);

            case STUDENT:
                return new Student(userType, username, userAttribute);

            case LEGAL_ENTITY:
                return new LegalEntity(userType, userAttribute, username);

            default:
                throw new IncorrectUserTypeException(type);
        }
    }
}
