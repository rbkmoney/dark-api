package com.rbkmoney.dark.api.util;

import com.rbkmoney.cabi.base.Rational;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@RequiredArgsConstructor(access = AccessLevel.NONE)
public class MathUtils {

    public static Rational covertToRational(BigDecimal value) {
        BigInteger denominator = value.scale() > 0 ? BigInteger.TEN.pow(value.scale()) : BigInteger.ONE;
        BigInteger numerator = value.remainder(BigDecimal.ONE)
                .movePointRight(value.scale()).toBigInteger()
                .add(value.toBigInteger().multiply(denominator));

        BigInteger gcd = denominator.gcd(numerator);
        denominator = denominator.divide(gcd);
        numerator = numerator.divide(gcd);

        if (numerator.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Too big numerator value: " + numerator);
        }
        if (denominator.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Too big denominator value: " + denominator);
        }

        return new Rational(numerator.longValue(), denominator.longValue());
    }

    public static BigDecimal convertFromRational(Rational rational) {
        return convertFromRational(rational, null);
    }

    public static BigDecimal convertFromRational(Rational rational, Integer scale) {
        BigDecimal numerator = BigDecimal.valueOf(rational.p);
        BigDecimal denominator = BigDecimal.valueOf(rational.q);
        if (scale != null) {
            return numerator.divide(denominator, scale, RoundingMode.HALF_UP);
        }
        return numerator.divide(denominator);
    }

}
