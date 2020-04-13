package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.Operator;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractUnaryOperator implements Operator<NumberEntity> {
    abstract protected Function<NumberEntity, NumberEntity> getFunction();

    @Override
    public List<NumberEntity> compute(List<NumberEntity> args) {
        return Collections.singletonList(getFunction().apply(args.get(0)));
    }

    @Override
    public int paramSize(int availableParameters) {
        return 1;
    }

    @Override
    abstract public String literal();
}
