package me.jersey.calculator.operator;

import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.impl.FactorialOperator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactorialOperatorTest {

    private FactorialOperator factor = new FactorialOperator();

    @Test
    public void testLiteral() {
        assertEquals("!", factor.literal());
    }

    @Test
    public void testSmallNumberFactorial() {
        assertEquals("0", factor.compute(Collections.singletonList(NumberEntity.of("0"))).get(0).toString());
        assertEquals("1", factor.compute(Collections.singletonList(NumberEntity.of("1"))).get(0).toString());
        assertEquals("120", factor.compute(Collections.singletonList(NumberEntity.of("5"))).get(0).toString());
        assertEquals("3628800", factor.compute(Collections.singletonList(NumberEntity.of("10"))).get(0).toString());
    }

    @Test
    public void tesLargerNumberFactorial() {
        // Cheat here because we set BOUND as 10
        assertEquals(String.valueOf(3628800 * 12 * 11), factor.compute(Collections.singletonList(NumberEntity.of("12"))).get(0).toString());
    }

    @Test
    public void testThrowBadInputException() {
        BadInputException exception = assertThrows(BadInputException.class,
                () -> factor.compute(Collections.singletonList(NumberEntity.of("-1"))));
        assertEquals("parameter is not a positive integer", exception.getMessage());
    }
}
