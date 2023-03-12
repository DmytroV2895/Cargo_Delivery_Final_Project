package com.varukha.webproject.util;

import com.varukha.webproject.util.calculator.util.CalculationUtil;
import com.varukha.webproject.util.calculator.util.DistanceCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void testCalculateOrderVolumeWeight() {
        double length = 50;
        double height = 20;
        double width = 30;
        double weight = 2.5;
        double expectedVolumeWeight = 2.5;
        double actualVolumeWeight = CalculationUtil.calculateOrderVolumeWeight(length, height, width, weight);
        assertEquals(expectedVolumeWeight, actualVolumeWeight);
    }

    @ParameterizedTest
    @MethodSource("isDeliveryDistance")
    void testCalculateDistanceBetweenTwoCities(String firstCity, String secondCity, double expectedResult) {
        double actualResult = DistanceCalculator.calculateDistanceBetweenTwoCities(firstCity, secondCity);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> isDeliveryDistance() {
        return Stream.of(
                Arguments.of("SUMY", "LVIV", 772.0),
                Arguments.of("CHARKIV", "ODESSA", 564.0),
                Arguments.of("LVIV", "ODESSA", 623.0)
        );
    }
}