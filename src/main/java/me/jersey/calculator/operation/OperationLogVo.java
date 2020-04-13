package me.jersey.calculator.operation;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OperationLogVo<T> {
    List<T> poped ;
    List<T> pushed;
}
