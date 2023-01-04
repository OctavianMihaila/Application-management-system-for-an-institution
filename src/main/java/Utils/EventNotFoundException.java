package Utils;

public class EventNotFoundException extends Exception {

    public EventNotFoundException(String eventTitle) {
        super("Event could not be found: " + eventTitle);
    }
}
