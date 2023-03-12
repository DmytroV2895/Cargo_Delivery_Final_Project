package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.calculator.CalculatorStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorFactoryTest {

    @Test
    void createCalculator() throws IncorrectInputException {

        // Test DOCUMENT
        CalculatorStrategy documentCalculator = PriceCalculatorFactory.createCalculator("DOCUMENT");
        assertTrue(documentCalculator instanceof DocumentPriceCalculator);

        // Test PARCEL
        CalculatorStrategy parcelCalculator = PriceCalculatorFactory.createCalculator("PARCEL");
        assertTrue(parcelCalculator instanceof ParcelPriceCalculator);

        // Test CARGO
        CalculatorStrategy cargoCalculator = PriceCalculatorFactory.createCalculator("CARGO");
        assertTrue(cargoCalculator instanceof CargoPriceCalculator);

        // Test unknown order type
        assertThrows(IncorrectInputException.class, () -> PriceCalculatorFactory.createCalculator("UNKNOWN"));
    }
}
