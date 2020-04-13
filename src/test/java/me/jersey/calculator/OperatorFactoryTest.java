package me.jersey.calculator;

import me.jersey.calculator.exceptions.DuplicateOperatorLiteralException;
import me.jersey.calculator.operator.Operator;
import me.jersey.calculator.operator.OperatorConfig;
import me.jersey.calculator.operator.OperatorFactory;
import me.jersey.calculator.operator.impl.AddOperator;
import me.jersey.calculator.operator.impl.MinusOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(OperatorFactoryTest.Config.class)
public class OperatorFactoryTest {

    @Autowired
    private OperatorFactory factory;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSupportOperator() {
        List<String> shouldSupport = Arrays.asList("+", "-", "*", "/", "sqrt", "undo", "clear");
        shouldSupport.forEach(literal -> {
            assertTrue(factory.has(literal), "Should support operator: " + literal);
        });
    }

    @Test
    public void testRegistrationOperator() {
        Operator testOperator = applicationContext.getBean("testOperator", Operator.class);
        Assertions.assertNotNull(testOperator);
        assertTrue(factory.has(testOperator.literal()), "OperatorFactory Should support operator: " + testOperator.literal());
    }

    @Test
    public void testDuplicationLiteralException() {
        Assertions.assertThrows(DuplicateOperatorLiteralException.class, () -> {
            new OperatorFactory(new AddOperator(), new MinusOperator(), new DuplilcateLiteralOperator());
        }, "Should throw DuplicateOperatorLiteralException for two operators having same literal.");
    }

    @Test
    public void testGetOperatorByLiteral() {
        TestOperation testOperation = new TestOperation();
        OperatorFactory operatorFactory = new OperatorFactory(testOperation, new AddOperator());
        assertTrue(operatorFactory.has(testOperation.literal()));
        assertEquals(testOperation, operatorFactory.getOperator(testOperation.literal()));
    }

    @Import(OperatorConfig.class)
    static class Config {
        @Bean
        public Operator testOperator() {
            return new TestOperation();
        }
    }

    static class TestOperation implements Operator {

        @Override
        public List compute(List args) {
            return null;
        }

        @Override
        public int paramSize(int availableParameters) {
            return 0;
        }

        @Override
        public String literal() {
            return this.getClass().getSimpleName();
        }
    }

    static class DuplilcateLiteralOperator implements Operator {

        @Override
        public List compute(List args) {
            return null;
        }

        @Override
        public int paramSize(int availableParameters) {
            return 0;
        }

        @Override
        public String literal() {
            return "+";
        }
    }

}
