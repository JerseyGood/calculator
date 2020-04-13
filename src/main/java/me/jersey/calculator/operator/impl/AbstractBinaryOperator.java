package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.Operator;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public abstract class AbstractBinaryOperator implements Operator<NumberEntity> {

    abstract protected BiFunction<NumberEntity, NumberEntity, NumberEntity> getFunction();

    @Override
    public List<NumberEntity> compute(List<NumberEntity> args) {
        NumberEntity val = getFunction().apply(args.get(0), args.get(1));
        return Collections.singletonList(val);

    }

    @Override
    public int paramSize(int availableParameters) {
        return 2;
    }

    @Override
    abstract public String literal();
}
