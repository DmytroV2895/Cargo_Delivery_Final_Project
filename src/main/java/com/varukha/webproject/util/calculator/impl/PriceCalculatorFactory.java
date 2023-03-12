package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.calculator.CalculatorStrategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class PriceCalculatorFactory used to create an instance of the necessary
 * calculator by order type that was received from {@link PriceCalculatorByOrderType}.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class PriceCalculatorFactory {

    public static final Logger logger = LogManager.getLogger();

    /**
     * Method createCalculator used to create an instance of the calculator by order type.
     *
     * @param orderType type of order.
     * @return an instance of calculator by necessary order type.
     */
   public static CalculatorStrategy createCalculator(String orderType) throws IncorrectInputException {
       logger.log(Level.DEBUG, "Create an instance of calculator by order type: " + orderType);
        return switch (orderType) {
            case "DOCUMENT" -> new DocumentPriceCalculator();
            case "PARCEL" -> new ParcelPriceCalculator();
            case "CARGO" -> new CargoPriceCalculator();
            default ->
                throw new IncorrectInputException(orderType + " - unknown order type.");
        };
    }
}
