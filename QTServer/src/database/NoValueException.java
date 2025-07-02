package database;

public class NoValueException extends RuntimeException {
    public NoValueException(){}
    public NoValueException(String message) {
        super(message);
    }
}
