package me.jersey.calculator.operator;

import me.jersey.calculator.exceptions.DividByZeroException;
import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.impl.AddOperator;
import me.jersey.calculator.operator.impl.DivideOperator;
import me.jersey.calculator.operator.impl.MinusOperator;
import me.jersey.calculator.operator.impl.MultiplyOperator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseOperatorTest {
    @Test
    public void testAdd() {
        AddOperator operator = new AddOperator();
        assertEquals("+", operator.literal());
        assertEquals(2, operator.paramSize());
        List<NumberEntity> vals = operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("20")));
        assertEquals("30", vals.get(0).toString());
    }

    @Test
    public void testMinus() {
        MinusOperator operator = new MinusOperator();
        assertEquals("-", operator.literal());
        assertEquals(2, operator.paramSize());
        List<NumberEntity> vals = operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("20")));
        assertEquals("-10", vals.get(0).toString());

    }

    @Test
    public void testMultiply() {
        MultiplyOperator operator = new MultiplyOperator();
        assertEquals("*", operator.literal());
        assertEquals(2, operator.paramSize());
        List<NumberEntity> vals = operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("20")));
        assertEquals("200", vals.get(0).toString());
    }

    @Test
    public void testDivide() {
        DivideOperator operator = new DivideOperator();
        assertEquals("/", operator.literal());
        assertEquals(2, operator.paramSize());
        List<NumberEntity> vals = operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("20")));
        assertEquals("0.5", vals.get(0).toString());
        vals = operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("3")));
        assertEquals("3.3333333333", vals.get(0).toString());
    }

    @Test
    public void testDivideZero() {
        DivideOperator operator = new DivideOperator();
        DividByZeroException e = assertThrows(DividByZeroException.class,
                () -> operator.compute(Arrays.asList(NumberEntity.of("10"), NumberEntity.of("0"))));
        assertEquals("cannot divide by zero", e.getMessage());
    }
}
