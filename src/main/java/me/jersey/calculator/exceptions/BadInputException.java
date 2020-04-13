package me.jersey.calculator.exceptions;

public class BadInputException extends RuntimeException {
    public BadInputException(String message) {
        super(message);
    }
}
