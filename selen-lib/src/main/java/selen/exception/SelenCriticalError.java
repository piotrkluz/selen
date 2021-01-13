package selen.exception;

public class SelenCriticalError extends RuntimeException {
    public SelenCriticalError() {
    }

    public SelenCriticalError(String message) {
        super(message);
    }

    public SelenCriticalError(String message, Throwable cause) {
        super(message, cause);
    }
}
