package me.jersey.calculator.exceptions;

public class DividByZeroException extends BadInputException {
    public DividByZeroException() {
        super("cannot divide by zero");
    }
}
