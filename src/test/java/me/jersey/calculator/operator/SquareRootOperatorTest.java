package me.jersey.calculator.operator;

import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.impl.SquareRootOperator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareRootOperatorTest {
    SquareRootOperator sqrt = new SquareRootOperator();

    @Test
    public void testSqrt() {
        assertEquals("sqrt", sqrt.literal());
        assertEquals(1, sqrt.paramSize());
        assertEquals("2", sqrt.compute(Collections.singletonList(NumberEntity.of("4"))).get(0).toString());
        assertEquals("1.4142135623", sqrt.compute(Collections.singletonList(NumberEntity.of("2"))).get(0).toString());
        assertEquals("0", sqrt.compute(Collections.singletonList(NumberEntity.of("0"))).get(0).toString());
    }

    @Test
    public void testThrowExceptionForNegative() {
        assertThrows(BadInputException.class, () -> sqrt.compute(Collections.singletonList(NumberEntity.of("-4"))));
    }
}
