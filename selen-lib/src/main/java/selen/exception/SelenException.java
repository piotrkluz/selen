package selen.exception;

public class SelenException extends RuntimeException {
    public SelenException(String message) {
        super(message);
    }

    public SelenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelenException(Throwable cause) {
        super(cause);
    }
}
