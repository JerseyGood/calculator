package me.jersey.calculator.operator;

import java.util.List;

public interface Operator<T> {
    /**
     *
     * @param args parameters for computing
     * @return a list of computing result.
     */
    List<T> compute(List<T> args);

    /**
     * For operators that have a dynamic length of parameters which depends on the size of available parameters when invoking
     *
     * @param availableParameters the number of parameters available
     * @return the size of parameters that required for computing
     */
    int paramSize(int availableParameters);

    /**
     * Most operators have a fixed length of parameters for computing.
     *
     * @return the size of parameters that required for computing
     */
    default int paramSize() {
        return paramSize(0);
    }

    /**
     * @return the unique literal of operator
     */
    String literal();
}
