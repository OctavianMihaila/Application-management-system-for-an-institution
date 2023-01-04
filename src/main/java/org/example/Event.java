package org.example;

import Users.User;
import Users.UserType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    private final UserType participantsType;
    private final String title;
    private final Boolean financedByMunicipality;
    private final Integer financeAmonut;
    private final Date scheduledTime;
    private final Date registrationDeadline;
    private final Integer maxAllowedParticipants;
    private final Integer registeredParticipants;
    private final List<User> registeredUsers;

    public Event(UserType participantsType, String title, Boolean financedByMunicipality, Integer financeAmonut,
                 Date scheduledTime, Date registrationDeadline, Integer maxAllowedParticipants) {
        this.participantsType = participantsType;
        this.title = title;
        this.financedByMunicipality = financedByMunicipality;
        this.financeAmonut = financeAmonut;
        this.scheduledTime = scheduledTime;
        this.registrationDeadline = registrationDeadline;
        this.maxAllowedParticipants = maxAllowedParticipants;
        this.registeredParticipants = 0;
        this.registeredUsers = new ArrayList<>();
    }

    public UserType getParticipantsType() {
        return participantsType;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getFinancedByMunicipality() {
        return financedByMunicipality;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public Integer getMaxAllowedParticipants() {
        return maxAllowedParticipants;
    }

    public Integer getRegisteredParticipants() {
        return registeredParticipants;
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void registerNewParticipant(User newParticipant) {
        this.registeredUsers.add(newParticipant);
    }
}
