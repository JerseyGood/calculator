package me.jersey.calculator.operator;


import me.jersey.calculator.exceptions.DuplicateOperatorLiteralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OperatorFactory {

    private final Map<String, Operator> supportedOperators;

    public Set<String> supportedOperators() {
        return supportedOperators.keySet();
    }
    @Autowired
    public OperatorFactory(Operator... operators) {
        supportedOperators = Arrays.stream(operators)
                .collect(Collectors.toMap(Operator::literal,
                        Function.identity(),
                        (u, v) -> {
                            throw new DuplicateOperatorLiteralException(
                                    String.format("Duplicate literal \"%s\" of [%s, %s]",
                                            u.literal(),
                                            u.getClass().getName(),
                                            v.getClass().getName()));
                        }));
    }

    public boolean has(String literal) {
        return supportedOperators.containsKey(literal);
    }

    public Operator getOperator(String literal) {
        return supportedOperators.get(literal);
    }
}
