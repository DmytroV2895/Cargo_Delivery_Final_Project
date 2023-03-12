package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParcelPriceCalculatorTest {

   private final ParcelPriceCalculator parcelPriceCalculator = new ParcelPriceCalculator();

    @ParameterizedTest
    @MethodSource("isGetOrderPriceParcel")
    void testGetOrderPriceParcel(String orderType,
                                 String weight,
                                 String length,
                                 String height,
                                 String width,
                                 String firstCity,
                                 String secondCity,
                                 String deliveryType,
                                 BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = parcelPriceCalculator.getOrderPrice(orderType, weight, length, height,
                width, firstCity, secondCity, deliveryType);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> isGetOrderPriceParcel() {
        return Stream.of(
                Arguments.of("PARCEL", "30", "35", "45", "25", "SUMY", "LVIV", "COURIER", BigDecimal.valueOf(300.360))
        );
    }
}