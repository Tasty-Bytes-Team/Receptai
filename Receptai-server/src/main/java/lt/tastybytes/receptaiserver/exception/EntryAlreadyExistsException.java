package lt.tastybytes.receptaiserver.exception;

public class EntryAlreadyExistsException extends Exception {
    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
