package me.jersey.calculator.operator.impl;

import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.number.NumberEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FactorialOperator extends AbstractUnaryOperator {
    // Try expand new operator

    private boolean isPositiveInteger(NumberEntity number) {
        return number.compareTo(NumberEntity.ZERO) >= 0 && !number.toString().contains(".");
    }

    // we could use a larger bound whose factorial is just lesser than Integer.MAX
    private static final int BOUND_INT = 10;
    private static final NumberEntity BOUND = NumberEntity.of(String.valueOf(BOUND_INT));


    @Override
    public String literal() {
        return "!";
    }

    @Override
    protected Function<NumberEntity, NumberEntity> getFunction() {
        return (number) -> {
            if (!isPositiveInteger(number)) throw new BadInputException("parameter is not a positive integer");
            if (number.compareTo(BOUND) <= 0) {
                int intResult = factorial(toInt(number));
                return ofInt(intResult);
            } else {
                NumberEntity result = number;
                for (NumberEntity i = number.subtract(NumberEntity.ONE); i.compareTo(BOUND) > 0; i = i.subtract(NumberEntity.ONE)) {
                    result = result.multiply(i);
                }
                NumberEntity boundFactorial = ofInt(factorial(BOUND_INT));
                return result.multiply(boundFactorial);
            }
        };
    }

    // An internal cache will improve the performance a lot.
    private int factorial(int n) {
        int factorial = n;
        for (int i = (n - 1); i > 1; i--) {
            factorial = factorial * i;
        }
        return factorial;
    }

    private int toInt(NumberEntity number) {
        return Integer.parseInt(number.toString());
    }

    private NumberEntity ofInt(int n) {
        return NumberEntity.of(String.valueOf(n));
    }
}
