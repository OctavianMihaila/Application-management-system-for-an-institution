package Utils;

public class IncorrectOfficeTypeException extends Exception {

    public IncorrectOfficeTypeException(String providedType) {
        super("Provided office type is wrong: " + providedType);
    }
}
