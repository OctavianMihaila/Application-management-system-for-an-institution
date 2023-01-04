package org.example;

import java.util.Date;


/**
 * Specific type of request used to create requests for
 * EVENT_ORGANIZATION and EVENT_REGISTRATION
 */
public class EventRequest extends Request {

    private final String title;
    private Boolean financedByMunicipality;
    private Integer financeAmonut;
    private Date scheduledTime;
    private Date registrationDeadline;
    private Integer maxAllowedParticipants;

    public EventRequest(String username, String requestType, Date dateAndTime, Integer priority,
                        String title, Boolean financedByMunicipality, Integer financeAmonut,
                        Date scheduledTime, Date registrationDeadline, Integer maxAllowedParticipants) {
        super(username, requestType, dateAndTime, priority);
        this.title = title;
        this.financedByMunicipality = financedByMunicipality;
        this.financeAmonut = financeAmonut;
        this.scheduledTime = scheduledTime;
        this.registrationDeadline = registrationDeadline;
        this.maxAllowedParticipants = maxAllowedParticipants;
    }

    // This constructor is used to create a Registration request.
    public EventRequest(String username, String requestType, Date dateAndTime, Integer priority, String title) {
        super(username, requestType, dateAndTime, priority);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getFinancedByMunicipality() {
        return financedByMunicipality;
    }

    public Integer getFinanceAmonut() {
        return financeAmonut;
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
}
