package me.jersey.calculator.operation;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class OperationLogRepositoryImpl<T> implements OperationLogRepository<T> {
    private final Stack<OperationLogVo<T>> stack = new Stack<>();

    @Override
    public void addLog(OperationLogVo<T> log) {
        this.stack.push(log);
    }

    @Override
    public OperationLogVo<T> removeLastLog() {
        return this.stack.pop();
    }

    @Override
    public void clear() {
        this.stack.clear();
    }
}
