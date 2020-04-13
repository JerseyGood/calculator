package me.jersey.calculator.operator.impl;

import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class MinusOperator extends AbstractBinaryOperator {

    @Override
    protected BiFunction<NumberEntity, NumberEntity, NumberEntity> getFunction() {
        return NumberEntity::subtract;
    }

   @Override
    public String literal() {
        return "-";
    }
}
