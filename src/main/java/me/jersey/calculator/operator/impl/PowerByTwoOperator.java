package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PowerByTwoOperator extends AbstractUnaryOperator {
    @Override
    protected Function<NumberEntity, NumberEntity> getFunction() {
        return n -> n.multiply(n);
    }

    @Override
    public String literal() {
        return "^2";
    }
}
