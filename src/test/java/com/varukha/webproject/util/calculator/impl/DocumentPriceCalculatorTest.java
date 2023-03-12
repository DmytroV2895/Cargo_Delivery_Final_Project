package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DocumentPriceCalculatorTest {

   private final DocumentPriceCalculator documentPriceCalculator = new DocumentPriceCalculator();

    @ParameterizedTest
    @MethodSource("isGetOrderPriceDocument")
    void testGetOrderPriceDocument(String orderType,
                                   String weight,
                                   String length,
                                   String height,
                                   String width,
                                   String firstCity,
                                   String secondCity,
                                   String deliveryType,
                                   BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = documentPriceCalculator.getOrderPrice(orderType, weight, length, height,
                width, firstCity, secondCity, deliveryType);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> isGetOrderPriceDocument() {
        return Stream.of(
                Arguments.of("DOCUMENT", "0.5", "35", "2", "25", "SUMY", "LVIV", "BY_TRUCK", BigDecimal.valueOf(113.60))
        );
    }
}