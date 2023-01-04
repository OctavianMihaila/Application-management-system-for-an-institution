package Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to get specific attributes from a received command.
 */
public class Parser {

    public static final Integer OFFSET_SPACES = 4;
    Integer position;
    String details;

    public Parser(String attributes) {
        this.details = attributes;
        this.position = attributes.indexOf(";", attributes.indexOf(";") + 1);
    }

    public String getFirstAttribute() {
        return details.substring(details.indexOf(" "), position)
                .replace(";", "").trim();
    }

    public String getSecondAttribute() {

        return details.substring(position, details.
                indexOf(";", position + 1)).replace(";", "").trim();
    }

    public String getThirdAttribute() {
        int semicolonCount = details.split(";").length;
        int offsetArgs = this.getSecondAttribute().length();

        if (semicolonCount >= OFFSET_SPACES) {
            return details.substring(position + offsetArgs + OFFSET_SPACES,
                    details.indexOf(";", position + offsetArgs + OFFSET_SPACES));
        }

        return null;
    }

    public String getFourthAttribute() {
        int semicolonCount = details.split(";").length;
        int offsetArgs = this.getSecondAttribute().length() + this.getThirdAttribute().length();

        if (semicolonCount >= OFFSET_SPACES) {
            return details.substring(position + offsetArgs
                    + OFFSET_SPACES, details.lastIndexOf(";")).replace(";", "").trim();
        }

        return null;
    }

    public Map<String, String> getEventAttributes() {
        Map<String, String> attributes = new HashMap<>();
        String eventDetails = details.substring(details.indexOf("|") + 1);
        Integer index = 1;

        while (!eventDetails.isEmpty()) {
            attributes.put("arg" + index, eventDetails.substring(1, eventDetails.indexOf("|")).trim());
            eventDetails = eventDetails.substring(1); // eliminate pipe.
            eventDetails = eventDetails.substring(eventDetails.indexOf("|") + 1);
            index++;
        }

        return attributes;
    }
}
