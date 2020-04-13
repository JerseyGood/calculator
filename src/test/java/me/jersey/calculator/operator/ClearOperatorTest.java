package me.jersey.calculator.operator;

import me.jersey.calculator.operator.impl.ClearOperator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearOperatorTest {
    private ClearOperator clearOperator=new ClearOperator();

    @Test
    public void testClearOperator() {
        assertEquals("clear", clearOperator.literal());
        assertEquals(10, clearOperator.paramSize(10),"Should be equal to the number of all available parameters");
        assertEquals(0, clearOperator.compute(Collections.emptyList()).size());
    }
}
