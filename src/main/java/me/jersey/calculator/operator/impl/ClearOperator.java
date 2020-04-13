package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.Operator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ClearOperator implements Operator<NumberEntity> {

    @Override
    public List<NumberEntity> compute(List<NumberEntity> args) {
        return Collections.emptyList();
    }

    @Override
    public int paramSize(int availableParameters) {
        return availableParameters;
    }

    @Override
    public String literal() {
        return "clear";
    }
}
