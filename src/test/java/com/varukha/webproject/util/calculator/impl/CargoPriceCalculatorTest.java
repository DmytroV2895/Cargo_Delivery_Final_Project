package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CargoPriceCalculatorTest {

    private final CargoPriceCalculator cargoPriceCalculator = new CargoPriceCalculator();

    @ParameterizedTest
    @MethodSource("isGetOrderPriceCargo")
    void testGetOrderPriceCargo(String orderType,
                                String weight,
                                String length,
                                String height,
                                String width,
                                String firstCity,
                                String secondCity,
                                String deliveryType,
                                BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = cargoPriceCalculator.getOrderPrice(orderType, weight, length, height,
                width, firstCity, secondCity, deliveryType);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> isGetOrderPriceCargo() {
        return Stream.of(
                Arguments.of("CARGO", "100", "35", "45", "25", "SUMY", "LVIV", "BY_TRUCK", BigDecimal.valueOf(1230.36))
        );
    }
}