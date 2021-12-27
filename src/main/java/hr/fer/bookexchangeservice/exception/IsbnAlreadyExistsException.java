package hr.fer.bookexchangeservice.exception;

public class IsbnAlreadyExistsException extends RuntimeException {
    public IsbnAlreadyExistsException(String msg) {
        super(msg);
    }
}
