package me.jersey.calculator.number;

import lombok.Value;
import me.jersey.calculator.exceptions.BadInputException;
import me.jersey.calculator.exceptions.DividByZeroException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ROUND_FLOOR;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Value
public class NumberEntity implements Comparable<NumberEntity> {

    BigDecimal value;

    private NumberEntity(BigDecimal bigDecimal) {
        // BigDecimal("1") does nt equal to BigDecimal("1.0") which will affect the equality of NumberEntity
        this.value = bigDecimal.stripTrailingZeros();
    }

    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final int MAX_PRECISION = 15;
    private static final int DISPLAY_PRECISION = 10;
    public static final NumberEntity ZERO = new NumberEntity(BigDecimal.ZERO);
    public static final NumberEntity ONE = new NumberEntity(BigDecimal.ONE);

    public static NumberEntity of(String token) {
        try {
            return new NumberEntity(new BigDecimal(token));
        } catch (NumberFormatException e) {
            throw new BadInputException(String.format("unsupported token \"%s\"", token));
        }
    }

    public NumberEntity subtract(NumberEntity other) {
        return new NumberEntity(this.value.subtract(other.value));
    }

    public NumberEntity multiply(NumberEntity other) {
        return new NumberEntity(this.value.multiply(other.value));
    }

    public NumberEntity add(NumberEntity other) {
        return new NumberEntity(this.value.add(other.value));
    }

    public NumberEntity divide(NumberEntity other) {
        if (other.value.compareTo(BigDecimal.ZERO) == 0) throw new DividByZeroException();
        BigDecimal divide = this.value.divide(other.value, MAX_PRECISION, RoundingMode.HALF_UP);
        return new NumberEntity(divide.stripTrailingZeros());
    }

    public NumberEntity sqrt() {
        if (this.value.compareTo(BigDecimal.ZERO) < 0) throw new BadInputException("parameter is negative");
        BigDecimal val = this.sqrt(this.value);
        return new NumberEntity(val.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return this.toString(DISPLAY_PRECISION);
    }

    // Babylonian method: https://stackoverflow.com/a/19743026/2211343
    // maybe too much since we only care about a precision of 15
    private BigDecimal sqrt(BigDecimal A) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, NumberEntity.MAX_PRECISION, ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, NumberEntity.MAX_PRECISION, ROUND_HALF_UP);
        }
        return x1;
    }

    private String toString(int maxPrecision) {
        // Only display 10 digit for precision.
        BigDecimal toDisplay = maxPrecision < value.scale() ?
                value.setScale(maxPrecision, ROUND_FLOOR) : value;
        return toDisplay.stripTrailingZeros().toPlainString();
    }

    @Override
    public int compareTo(NumberEntity o) {
        return this.value.compareTo(o.value);
    }
}
