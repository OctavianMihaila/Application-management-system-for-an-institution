package Utils;
/**
 *
 */
public class IncorrectUserTypeException extends Exception {
    public IncorrectUserTypeException(String providedType) {
        super("Provided user type is wrong: " + providedType);
    }
}
