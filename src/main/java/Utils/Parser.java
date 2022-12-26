package Utils;

import static Utils.Constants.OFFSET_SPACES;

public class Parser {
    Integer position;
    String userDetails;

    public Parser(String attributes) {
        this.userDetails = attributes;
        this.position = attributes.indexOf(";", attributes.indexOf(";") + 1);
    }

    public String getFirstAttribute() {

        return userDetails.substring(userDetails.indexOf(" "), position)
                .replace(";", "").trim();
    }

    public String getSecondAttribute() {

        return userDetails.substring(position, userDetails.
                indexOf(";", position + 1)).replace(";", "").trim();
    }

    public String getThirdAttribute() {
        int semicolonCount = userDetails.split(";").length;
        int offsetArgs = this.getSecondAttribute().length();

        if (semicolonCount >= OFFSET_SPACES) {
            return userDetails.substring(position + offsetArgs
                    + OFFSET_SPACES, userDetails.indexOf(";", position + offsetArgs + OFFSET_SPACES));
        }

        return null;
    }

    public String getFourthAttribute() {
        int semicolonCount = userDetails.split(";").length;
        int offsetArgs = this.getSecondAttribute().length() + this.getThirdAttribute().length();

        if (semicolonCount >= OFFSET_SPACES) {
            return userDetails.substring(position + offsetArgs
                    + OFFSET_SPACES, userDetails.lastIndexOf(";")).replace(";", "").trim();
        }

        return null;
    }
}
