package me.jersey.calculator.operator;

import me.jersey.calculator.number.NumberEntity;
import me.jersey.calculator.operator.impl.PowerByTwoOperator;
import me.jersey.calculator.operator.impl.PowerOperator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerOperatorTest {

    private PowerOperator power = new PowerOperator();
    private PowerByTwoOperator powerTwo = new PowerByTwoOperator();

    @Test
    public void testPowerByTwo() {
        assertEquals("^2", powerTwo.literal());
        assertEquals(1, powerTwo.paramSize());
        assertEquals("4", powerTwo.compute(Arrays.asList(NumberEntity.of("2"), NumberEntity.of("20"))).get(0).toString());
        assertEquals("0", powerTwo.compute(Arrays.asList(NumberEntity.of("0"))).get(0).toString());
        assertEquals("4", powerTwo.compute(Arrays.asList(NumberEntity.of("-2"))).get(0).toString());
    }

    @Test
    public void testPower() {
        assertEquals("^", power.literal());
        assertEquals(2, power.paramSize());
        assertEquals("4", power.compute(Arrays.asList(NumberEntity.of("2"), NumberEntity.of("2"))).get(0).toString());
        assertEquals("1", power.compute(Arrays.asList(NumberEntity.of("2"), NumberEntity.of("0"))).get(0).toString());
        assertEquals("0", power.compute(Arrays.asList(NumberEntity.of("0"), NumberEntity.of("2"))).get(0).toString());
        assertEquals("-8", power.compute(Arrays.asList(NumberEntity.of("-2"),NumberEntity.of("3"))).get(0).toString());
    }
}
