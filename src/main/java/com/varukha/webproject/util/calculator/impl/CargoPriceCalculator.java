package com.varukha.webproject.util.calculator.impl;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.calculator.util.DistanceCalculator;
import com.varukha.webproject.util.calculator.CalculatorStrategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.varukha.webproject.util.calculator.PriceListConstants.*;
import static com.varukha.webproject.util.calculator.util.CalculationUtil.calculateOrderVolumeWeight;


/**
 * Class CargoPriceCalculator implements methods for calculating order price by order type - <strong>Cargo</strong>.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class CargoPriceCalculator implements CalculatorStrategy {

    public static final Logger logger = LogManager.getLogger();

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
    @Override
    public BigDecimal getOrderPrice(String orderType,
                                    String weight,
                                    String length,
                                    String height,
                                    String width,
                                    String firstCity,
                                    String secondCity,
                                    String deliveryType) throws IncorrectInputException {
        logger.log(Level.DEBUG, "Calculate delivery order price by order type: " + orderType +
                " weight: " + weight + " length:" + length +
                " height: " + height + " width: " + width +
                " firstCity: " + firstCity +
                " secondCity: " + secondCity + " deliveryType: " + deliveryType);

        double orderLengthToDouble = Double.parseDouble(length);
        double orderHeightToDouble = Double.parseDouble(height);
        double orderWidthToDouble = Double.parseDouble(width);
        double orderWeightToDouble = Double.parseDouble(weight);

        double deliveryDistance = DistanceCalculator.calculateDistanceBetweenTwoCities(firstCity, secondCity);
        double cargoVolumeWeight = calculateOrderVolumeWeight(orderLengthToDouble,
                                                              orderHeightToDouble,
                                                              orderWidthToDouble,
                                                              orderWeightToDouble);

        BigDecimal distancePrice = getPriceByDeliveryDistance(deliveryDistance);
        BigDecimal deliveryPriceByDeliveryType = getPriceByDeliveryType(deliveryType, firstCity, secondCity);

        double distancePriceToDouble = Double.parseDouble(String.valueOf(distancePrice));
        double deliveryPriceByDeliveryTypeToDouble = Double.parseDouble(String.valueOf(deliveryPriceByDeliveryType));

        double deliveryCargoPrice = 0;

        if (cargoVolumeWeight >= 31 && cargoVolumeWeight <= 249) {
            deliveryCargoPrice = BASE_PRICE_OF_DELIVERY_BY_CARGO_WEIGHT_31_249_kg +
                    distancePriceToDouble + deliveryPriceByDeliveryTypeToDouble;
        }
        if (cargoVolumeWeight >= 250 && cargoVolumeWeight <= 599) {
            deliveryCargoPrice = BASE_PRICE_OF_DELIVERY_BY_CARGO_WEIGHT_250_599_kg +
                    distancePriceToDouble + deliveryPriceByDeliveryTypeToDouble;
        }
        if (cargoVolumeWeight >= 600 && cargoVolumeWeight <= 1000) {
            deliveryCargoPrice = BASE_PRICE_OF_DELIVERY_BY_CARGO_WEIGHT_600_1000_kg +
                    distancePriceToDouble + deliveryPriceByDeliveryTypeToDouble;
        }
        BigDecimal deliveryCargoPriceResult = BigDecimal.valueOf(deliveryCargoPrice);
        return deliveryCargoPriceResult.setScale(2, RoundingMode.HALF_UP);
    }

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
    @Override
    public BigDecimal getPriceByDeliveryType(String deliveryType, String firstCity, String secondCity) throws IncorrectInputException {
        logger.log(Level.DEBUG, "Calculate price by delivery type: " + deliveryType +
                "firstCity: " + firstCity + "secondCity: " + secondCity);

        double deliveryPriceByDeliveryType = 0;
        switch (deliveryType) {
            case "COURIER" -> {
                if (firstCity.equals(secondCity)) {
                    deliveryPriceByDeliveryType = DELIVERY_PRICE_OF_CARGO_BY_DELIVERY_TYPE_COURIER_ONE_CITY;
                } else {
                    logger.log(Level.ERROR, "Unavailable delivery type:" + deliveryType );
                    throw new IncorrectInputException("Delivery type by courier for document order type " +
                            "is available within one city! Current input: First city: " + firstCity + "Second city: " + secondCity);
                }
            }
            case "BY_TRUCK" -> {
                if (!firstCity.equals(secondCity)) {
                    deliveryPriceByDeliveryType = DELIVERY_PRICE_OF_CARGO_BY_DELIVERY_TYPE_BY_TRUCK_ONE_CITY;
                } else {
                    deliveryPriceByDeliveryType = DELIVERY_PRICE_OF_CARGO_BY_DELIVERY_TYPE_BY_TRUCK_TWO_CITY;
                }
            }
        }
        BigDecimal deliveryPriceByDeliveryTypeResult = BigDecimal.valueOf(deliveryPriceByDeliveryType);
        return deliveryPriceByDeliveryTypeResult.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Method getPriceByDeliveryDistance used to calculate price that depend on the order type.
     * In that method take into account delivery type and delivery location.
     * Depend on location (within one city or between two cities) price is changed.
     *
     * @param deliveryDistance delivery distance of order.
     * @return price by delivery distance.
     */
    @Override
    public BigDecimal getPriceByDeliveryDistance(double deliveryDistance) {
        logger.log(Level.DEBUG, "Calculate price by delivery distance: " + deliveryDistance);
        return BigDecimal.valueOf(DELIVERY_PRICE_OF_CARGO_BY_DISTANCE_BY_1_KM * deliveryDistance);
    }
}

