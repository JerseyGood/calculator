package me.jersey.calculator;

import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.exceptions.DividByZeroException;
import me.jersey.calculator.number.NumberEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class NumberEntityTest {

    @Test
    public void testStringConversion() {
        Arrays.asList("0", "1", "9.9", "-5", "-0.0003", "1234567890123456789").forEach(s -> {
            assertEquals(s, NumberEntity.of(s).toString());
        });
        // Should display 10 precision and trim unnecessary zeros
        Arrays.asList(
                new String[]{"1.00", "1"},
                new String[]{"1000.0010000", "1000.001"},
                new String[]{"0.012345678998765", "0.0123456789"}
        ).forEach(test -> {
            assertEquals(test[1], NumberEntity.of(test[0]).toString());
        });
    }

    @Test
    public void testAdd() {
        assertEquals(NumberEntity.of("3"), NumberEntity.of("1").add(NumberEntity.of("2")));
        assertEquals(NumberEntity.ZERO, NumberEntity.of("-1").add(NumberEntity.of("1")));
        assertEquals(NumberEntity.of("3.3"), NumberEntity.of("1.15").add(NumberEntity.of("2.150")));
    }

    @Test
    public void testSubstract() {
        assertEquals(NumberEntity.of("-1"), NumberEntity.of("1").subtract(NumberEntity.of("2")));
        assertEquals(NumberEntity.ZERO, NumberEntity.of("1").subtract(NumberEntity.of("1")));
        assertEquals(NumberEntity.of("1.0"), NumberEntity.of("2.15").subtract(NumberEntity.of("1.150")));
    }

    @Test
    public void testMultiply() {
        assertEquals(NumberEntity.of("-2"), NumberEntity.of("-1").multiply(NumberEntity.of("2")));
        assertEquals(NumberEntity.ZERO, NumberEntity.of("0").multiply(NumberEntity.of("100")));
        assertEquals(NumberEntity.of("2.3"), NumberEntity.of("2").multiply(NumberEntity.of("1.150")));
    }

    @Test
    public void testDivide() {
        assertEquals(NumberEntity.of("-0.5"), NumberEntity.of("-1").divide(NumberEntity.of("2")));
        assertEquals(NumberEntity.ZERO, NumberEntity.of("0").divide(NumberEntity.of("1")));
        assertEquals(NumberEntity.of("1.0"), NumberEntity.of("2").divide(NumberEntity.of("2")));
        assertEquals("3.3333333333", NumberEntity.of("10").divide(NumberEntity.of("3")).toString());
        assertThrows(DividByZeroException.class, () -> NumberEntity.of("10").divide(NumberEntity.of("0")));
    }

    @Test
    public void testSqrt() {
        assertEquals("2", NumberEntity.of("4").sqrt().toString());
        assertEquals("1.4142135623", NumberEntity.of("2").sqrt().toString());
        assertEquals("0", NumberEntity.of("0").sqrt().toString());
        assertThrows(BadInputException.class, () -> NumberEntity.of("-10").sqrt());
    }
}
