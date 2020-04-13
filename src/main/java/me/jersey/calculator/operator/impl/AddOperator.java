package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AddOperator extends AbstractBinaryOperator {

    @Override
    protected BiFunction<NumberEntity, NumberEntity, NumberEntity> getFunction() {
        return (a, b) -> a.add(b);
    }

    @Override
    public String literal() {
        return "+";
    }
}
