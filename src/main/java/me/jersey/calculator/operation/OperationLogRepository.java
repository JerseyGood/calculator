package me.jersey.calculator.operation;

public interface OperationLogRepository<T> {

    void addLog(OperationLogVo<T> log);

    OperationLogVo<T> removeLastLog();

    void clear();
}
