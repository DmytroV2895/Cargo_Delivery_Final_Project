package com.varukha.webproject.util;


import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.model.dao.impl.UserDAOImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


class CalculatorTest {


    @Test
    void calculateOrderVolumeWeight() {
    }


    @ParameterizedTest
    @MethodSource("isOrderVolume")
    void calculateOrderVolume(double length, double height, double width, double expectedResult) {
        double actualResult = Calculator.calculateOrderVolume(length, height, width);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isDeliveryDistance")
    void testDeliveryDistancePriceByOrderType(String orderType, int deliveryDistance, BigDecimal expectedResult) {
        BigDecimal actualResult = Calculator.deliveryDistancePriceByOrderType(orderType, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isDocumentDeliveryPrice")
    void testTotalDocumentDeliveryPrice(String orderType, String deliveryDistance, BigDecimal expectedResult) {
        BigDecimal actualResult = Calculator.totalDocumentDeliveryPrice(orderType, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isParcelDeliveryPrice")
    void testParcelDeliveryPrice(String orderType,
                                 double actualWeight,
                                 double length,
                                 double height,
                                 double width,
                                 double deliveryDistance,
                                 BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = Calculator.parcelDeliveryPrice(orderType, actualWeight, length, height, width, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isCargoDeliveryPrice")
    void testCargoDeliveryPrice(String orderType,
                                double actualWeight,
                                double length,
                                double height,
                                double width,
                                double deliveryDistance,
                                BigDecimal expectedResult) {
        BigDecimal actualResult = Calculator.cargoDeliveryPrice(orderType, actualWeight, length, height, width, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isCalculateDeliveryPrice")
    void testCalculateDeliveryPrice(String orderType,
                                    String actualWeight,
                                    String length,
                                    String height,
                                    String width,
                                    String deliveryDistance,
                                    BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = Calculator.calculateDeliveryPrice(orderType, actualWeight, length, height, width, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isCalculateTotalServiceDeliveryPrice")
    void testCalculateTotalServiceDeliveryPrice(String orderType,
                                                String orderPrice,
                                                String actualWeight,
                                                String length,
                                                String height,
                                                String width,
                                                String deliveryDistance,
                                                BigDecimal expectedResult) throws IncorrectInputException {
        BigDecimal actualResult = Calculator.calculateTotalServiceDeliveryPrice(orderType, orderPrice, actualWeight, length, height, width, deliveryDistance);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("isDefineOrderType")
    void testDefineOrderType(String weight, String length, String height, String width, String expectedResult) throws IncorrectInputException {
        String actualResult = Calculator.defineOrderType(weight, length, height, width);
        assertEquals(expectedResult, actualResult);
    }


    public static Stream<Arguments> isOrderVolume() {
        return Stream.of(
                Arguments.of(50.0, 100.0, 20.0, 1000.0),
                Arguments.of(0.0, 0.0, 0.0, 0.0),
                Arguments.of(50.0, 0.0, 0.0, 0.0)

        );
    }

    public static Stream<Arguments> isDocumentDeliveryPrice() {
        return Stream.of(
                Arguments.of("DOCUMENT", "1000", BigDecimal.valueOf(95.0))
        );
    }

    public static Stream<Arguments> isParcelDeliveryPrice() {
        return Stream.of(
                Arguments.of("PARCEL", 30.0, 100.0, 150.0, 100.0, 1500, BigDecimal.valueOf(315.0))
        );
    }

    public static Stream<Arguments> isCargoDeliveryPrice() {
        return Stream.of(
                Arguments.of("CARGO", 150.0, 100.0, 150.0, 100.0, 1500, BigDecimal.valueOf(2380.0))
        );
    }

    public static Stream<Arguments> isCalculateDeliveryPrice() {
        return Stream.of(
                Arguments.of("DOCUMENT", "0.5", "25.0", "0.0", "35.0", "1500", BigDecimal.valueOf(195.0)),
                Arguments.of("PARCEL", "30.0", "100.0", "150.0", "100.0", "1500", BigDecimal.valueOf(510.0)),
                Arguments.of("CARGO", "150.0", "100.0", "150.0", "100.0", "1500", BigDecimal.valueOf(4630.0))
        );
    }

    public static Stream<Arguments> isCalculateTotalServiceDeliveryPrice() {
        return Stream.of(
                Arguments.of("DOCUMENT", "100", "0.5", "25.0", "0.0", "35.0", "1500", BigDecimal.valueOf(295.0)),
                Arguments.of("PARCEL", "500", "30.0", "100.0", "150.0", "100.0", "1500", BigDecimal.valueOf(1010.0)),
                Arguments.of("CARGO", "2000", "150.0", "100.0", "150.0", "100.0", "1500", BigDecimal.valueOf(6630.0))
        );
    }

    public static Stream<Arguments> isDeliveryDistance() {
        return Stream.of(
                Arguments.of("DOCUMENT", 1000, BigDecimal.valueOf(50.0)),
                Arguments.of("PARCEL", 1000, BigDecimal.valueOf(130.0)),
                Arguments.of("CARGO", 1000, BigDecimal.valueOf(1500.0))
        );
    }

    public static Stream<Arguments> isDefineOrderType() {
        return Stream.of(
                Arguments.of("0.5", "25.0", "0.0", "35.0", "DOCUMENT"),
                Arguments.of("30.0", "100.0", "150.0", "100.0", "PARCEL"),
                Arguments.of("150.0", "100.0", "150.0", "100.0", "CARGO")
        );


    }
}