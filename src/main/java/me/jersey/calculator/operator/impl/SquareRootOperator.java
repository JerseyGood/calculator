package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SquareRootOperator extends AbstractUnaryOperator {

    @Override
    protected Function<NumberEntity, NumberEntity> getFunction() {
        return NumberEntity::sqrt;
    }

    @Override
    public String literal() {
        return "sqrt";
    }
}
