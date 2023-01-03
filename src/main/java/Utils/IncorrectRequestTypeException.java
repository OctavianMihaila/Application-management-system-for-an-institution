package Utils;

public class IncorrectRequestTypeException extends Exception {

    public IncorrectRequestTypeException(String providedRequestType) {
        super("Provided request type is wrong: " + providedRequestType);
    }
}
