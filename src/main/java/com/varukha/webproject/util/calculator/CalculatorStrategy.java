package com.varukha.webproject.util.calculator;

import com.varukha.webproject.exception.IncorrectInputException;

import java.math.BigDecimal;

/**
 * Interface CalculatorStrategy contain contracts of methods for
 * calculating delivery price by order type and total delivery price.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface CalculatorStrategy {

    /**
     * Method getOrderPrice used to calculate delivery price by order type.
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
    BigDecimal getOrderPrice(String orderType,
                             String weight,
                             String length,
                             String height,
                             String width,
                             String firstCity,
                             String secondCity,
                             String deliveryType) throws IncorrectInputException;

    /**
     * Method getPriceByDeliveryType used to calculate price that depend on the order type.
     * In that method take into account delivery type and delivery location.
     * Depend on location (within one city or between two cities) price is changed.
     *
     * @param firstCity    sender city.
     * @param secondCity   receiver city.
     * @param deliveryType order delivery type.
     * @return price by delivery type.
     * @throws IncorrectInputException is an exception that throws depending on
     * the availability of delivery within the same city or different cities and the type of order delivery.
     */

     BigDecimal getPriceByDeliveryType(String deliveryType, String firstCity, String secondCity) throws IncorrectInputException;

    /**
     * Method getPriceByDeliveryDistance used to calculate price that depend on the order type.
     * In that method take into account delivery type and delivery location.
     * Depend on location (within one city or between two cities) price is changed.
     *
     * @param deliveryDistance delivery distance of order.
     * @return price by delivery distance.
     */
    BigDecimal getPriceByDeliveryDistance(double deliveryDistance);
}
