package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TotalDeliveryPriceCalculatorTest {

    @ParameterizedTest
    @MethodSource("isCalculateTotalPriceOfDeliveryService")
    void calculateTotalPriceOfDeliveryService(
            String orderType,
            String orderPrice,
            String weight,
            String length,
            String height,
            String width,
            String firstCity,
            String secondCity,
            String deliveryType,
            BigDecimal expectedResult) throws IncorrectInputException {

        try (MockedStatic<PriceCalculatorByOrderType> priceCalculatorByOrderTypeMockedStatic = Mockito.mockStatic(PriceCalculatorByOrderType.class)) {
            BigDecimal deliveryPrice = BigDecimal.valueOf(50.00);
            priceCalculatorByOrderTypeMockedStatic.when(() -> PriceCalculatorByOrderType.calculateDeliveryPrice(anyString(), anyString(), anyString(), anyString(),
                            anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(deliveryPrice);

            BigDecimal actualTotalPrice = TotalDeliveryPriceCalculator.calculateTotalPriceOfDeliveryService(orderType, orderPrice, weight, length, height, width, firstCity, secondCity, deliveryType);

            assertEquals(expectedResult, actualTotalPrice);
        }
    }

    public static Stream<Arguments> isCalculateTotalPriceOfDeliveryService() {
        return Stream.of(
                Arguments.of("DOCUMENT", "100", "0.5", "25.0", "15", "35.0", "SUMY", "ODESSA", "BY_TRUCK", BigDecimal.valueOf(150.00)),
                Arguments.of("PARCEL", "500", "30.0", "100.0", "150.0", "100.0", "SUMY", "ODESSA", "BY_TRUCK", BigDecimal.valueOf(550.0)),
                Arguments.of("CARGO", "2000", "150.0", "100.0", "150.0", "100.0", "SUMY", "ODESSA", "BY_TRUCK", BigDecimal.valueOf(2050.0))
        );
    }
}
