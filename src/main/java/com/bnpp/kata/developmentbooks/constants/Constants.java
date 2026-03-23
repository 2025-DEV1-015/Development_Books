package com.bnpp.kata.developmentbooks.constants;


import java.util.Map;

public final class Constants {

    private Constants() {

    }

    public static final double BASE_PRICE = 50.0;
    public static final double ZERO_DOUBLE = 0.0;
    public static final int ZERO_INT = 0;
    public static final int ONE = 1;
    public static final int HUNDRED = 100;
    public static final Map<Integer, Double> DISCOUNT = Map.of(
            1, 0.0,
            2, 0.05,
            3, 0.10,
            4, 0.20,
            5, 0.25
    );
}