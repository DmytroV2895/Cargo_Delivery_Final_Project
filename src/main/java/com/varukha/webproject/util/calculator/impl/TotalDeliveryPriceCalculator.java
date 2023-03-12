package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class TotalDeliveryPriceCalculator used to calculate total price of delivery service.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class TotalDeliveryPriceCalculator {

    public static final Logger logger = LogManager.getLogger();


    /**
     * Method calculateTotalPriceOfDeliveryService used to calculate total price of delivery service.
     *
     * @param orderPrice   order price
     * @param orderType    type of order from user input
     * @param weight       order weight
     * @param height       order height
     * @param length       order length
     * @param width        order width
     * @param firstCity    sender city
     * @param secondCity   receiver city
     * @param deliveryType order delivery type
     * @return total price for all service
     */
    public static BigDecimal calculateTotalPriceOfDeliveryService(String orderType,
                                                                  String orderPrice,
                                                                  String weight,
                                                                  String length,
                                                                  String height,
                                                                  String width,
                                                                  String firstCity,
                                                                  String secondCity,
                                                                  String deliveryType) throws IncorrectInputException {

        logger.log(Level.DEBUG, "Calculate total delivery price: " + orderType +
                " weight: " + weight + " length: " + length +
                " height: " + height + " width: " + width +
                " firstCity: " + firstCity +
                " secondCity: " + secondCity + " deliveryType: " + deliveryType);

        BigDecimal deliveryPriceByOrderType = PriceCalculatorByOrderType.calculateDeliveryPrice(orderType,
                weight,
                length,
                height,
                width,
                firstCity,
                secondCity,
                deliveryType);
        double orderPriceToDouble = Double.parseDouble(orderPrice);
        double deliveryPriceByOrderTypeToDouble = Double.parseDouble(String.valueOf(deliveryPriceByOrderType));
        BigDecimal totalDeliveryPrice = BigDecimal.valueOf(deliveryPriceByOrderTypeToDouble + orderPriceToDouble);
        return totalDeliveryPrice.setScale(1, RoundingMode.HALF_UP);
    }
}
