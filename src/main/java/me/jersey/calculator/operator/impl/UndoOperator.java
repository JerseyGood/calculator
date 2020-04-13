package me.jersey.calculator.operator.impl;

import me.jersey.calculator.operator.Operator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UndoOperator implements Operator {

    @Override
    public List compute(List args) {
        return null;
    }

    @Override
    public int paramSize(int availableParameters) {
        return 0;
    }

    @Override
    public String literal() {
        return "undo";
    }
}
