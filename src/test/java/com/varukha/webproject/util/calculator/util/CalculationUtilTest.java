package com.varukha.webproject.util.calculator.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationUtilTest {

    @ParameterizedTest
    @MethodSource("isOrderVolumeWeight")
    void calculateOrderVolumeWeight(double length, double height, double width, double weight, double expectedResult) {
        double actualResult = CalculationUtil.calculateOrderVolumeWeight(length, height, width, weight);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isOrderVolume")
    void calculateOrderVolume(double length, double height, double width, double expectedResult) {
        double actualResult = CalculationUtil.calculateOrderVolume(length, height, width);
        assertEquals(expectedResult, actualResult);
    }

    public static Stream<Arguments> isOrderVolume() {
        return Stream.of(
                Arguments.of(50.0, 100.0, 20.0, 1000.0),
                Arguments.of(0.0, 0.0, 0.0, 0.0, 0,0),
                Arguments.of(50.0, 0.0, 0.0, 0.0, 0.0)

        );
    }

    public static Stream<Arguments> isOrderVolumeWeight() {
        return Stream.of(
                Arguments.of(50.0, 40.0, 30.0, 20.0, 20.0),
                Arguments.of(0, 0, 0, 0, 0),
                Arguments.of(50.0, 0, 0, 0, 0),
                Arguments.of(10.0, 13, 0, 0, 0)

        );
    }
}
