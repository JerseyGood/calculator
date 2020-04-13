package me.jersey.calculator.operator.impl;

import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class PowerOperator extends AbstractBinaryOperator {

    private void checkArgument(NumberEntity base, NumberEntity power) {
        // for simplicity we only support positive number as power
        if (!isPositiveInteger(power)) throw new BadInputException("parameter is not a positive integer");
        if (power.compareTo(NumberEntity.of("999999999")) > 0)
            throw new BadInputException("cannot support power larger than 999999999");
    }

    private boolean isPositiveInteger(NumberEntity power) {
        return power.compareTo(NumberEntity.ZERO) >= 0 && !power.toString().contains(".");
    }

    @Override
    protected BiFunction<NumberEntity, NumberEntity, NumberEntity> getFunction() {
        return (x, y) -> {
            if (x.compareTo(NumberEntity.ZERO) == 0) return NumberEntity.ZERO;
            if (y.compareTo(NumberEntity.ZERO) == 0) return NumberEntity.ONE;
            checkArgument(x, y);
            // could optimize to use int for smaller numbers
            NumberEntity num = x;
            for (NumberEntity i = NumberEntity.ONE; i.compareTo(y) < 0; i = i.add(NumberEntity.ONE)) {
                num = num.multiply(x);
            }
            return num;
        };
    }

    @Override
    public String literal() {
        return "^";
    }
}
