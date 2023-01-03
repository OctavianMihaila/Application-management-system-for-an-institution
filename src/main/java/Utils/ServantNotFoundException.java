package Utils;

public class ServantNotFoundException extends Exception {

    public ServantNotFoundException(String servantName) {
        super("Provided servant could not be found: " + servantName);
    }
}
