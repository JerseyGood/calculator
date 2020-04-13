package me.jersey.calculator.exceptions;

public class InsufficientParameterException extends BadInputException {
    public InsufficientParameterException() {
        super("insufficient parameters");
    }
}
