package hr.fer.bookexchangeservice.exception;

public class AdvertNotFoundException extends RuntimeException {
    public AdvertNotFoundException(String msg) {
        super(msg);
    }
}
