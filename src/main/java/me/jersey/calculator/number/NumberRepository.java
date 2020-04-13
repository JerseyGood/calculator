package me.jersey.calculator.number;

import java.util.List;

public interface NumberRepository {
    NumberEntity pop();

    NumberRepository push(NumberEntity num);

    NumberRepository addAll(List<NumberEntity> numbers);

    String getStackString();

    int size();

    void clear();
}
