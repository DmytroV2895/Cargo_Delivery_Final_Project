package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.calculator.CalculatorStrategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 * Class PriceCalculatorByOrderType used to select the necessary
 * calculator by order type from {@link PriceCalculatorFactory} to provide calculation.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class PriceCalculatorByOrderType {

    public static final Logger logger = LogManager.getLogger();

    /**
     * Method calculateDeliveryPrice used to select the necessary
     * calculator to provide calculation of order delivery price by order type.
     *
     * @param orderType    type of order from user input.
     * @param weight       order weight.
     * @param height       order height.
     * @param length       order length.
     * @param width        order width.
     * @param firstCity    sender city.
     * @param secondCity   receiver city.
     * @param deliveryType order delivery type.
     * @return delivery price by order type.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    public static BigDecimal calculateDeliveryPrice(String orderType,
                                                    String weight,
                                                    String length,
                                                    String height,
                                                    String width,
                                                    String firstCity,
                                                    String secondCity,
                                                    String deliveryType) throws IncorrectInputException {
        logger.log(Level.DEBUG, "Calculate delivery order price by order type: " + orderType +
                "weight: " + weight + "length:" + length +
                "height: " + height + "width: " + width +
                "firstCity: " + firstCity +
                "secondCity: " + secondCity + "deliveryType: " + deliveryType);

        CalculatorStrategy calculatorStrategy = PriceCalculatorFactory.createCalculator(orderType);
        logger.log(Level.DEBUG, "Selected calculatorStrategy: " + calculatorStrategy);
        return calculatorStrategy.getOrderPrice(orderType, weight, length, height, width, firstCity, secondCity, deliveryType);
    }
}


