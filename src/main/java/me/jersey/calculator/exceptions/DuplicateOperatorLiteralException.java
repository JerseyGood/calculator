package me.jersey.calculator.exceptions;

public class DuplicateOperatorLiteralException extends RuntimeException {
    public DuplicateOperatorLiteralException(String message) {
        super(message);
    }
}
