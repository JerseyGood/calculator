package me.jersey.calculator.operation;

import me.jersey.calculator.exceptions.InsufficientParameterException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.Operator;

public interface OperationService {

    void applyOperator(Operator<NumberEntity> op) throws InsufficientParameterException;
    void pushNumber(NumberEntity number);
}
