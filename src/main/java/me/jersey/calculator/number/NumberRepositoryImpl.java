package me.jersey.calculator.number;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Component
public class NumberRepositoryImpl implements NumberRepository {

    private final Stack<NumberEntity> stack = new Stack<>();

    @Override
    public NumberEntity pop() {
        return stack.pop();
    }

    @Override
    public NumberRepository push(NumberEntity num) {
        stack.push(num);
        return this;
    }

    @Override
    public NumberRepository addAll(List<NumberEntity> numbers) {
        this.stack.addAll(numbers);
        return this;
    }

    @Override
    public String getStackString() {
        return stack.stream().map(NumberEntity::toString).collect(Collectors.joining(" "));
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public void clear() {
        this.stack.clear();
    }
}
