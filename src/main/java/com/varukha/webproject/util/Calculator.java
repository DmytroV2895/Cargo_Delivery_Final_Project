package com.varukha.webproject.util;


import com.varukha.webproject.exception.IncorrectInputException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Class Calculator.
 * Used for calculate delivery price of order, depending on order type, actual weight (for document),
 * volume weight and delivery distance
 *
 * @author Dmytro Varukha
 */

public class Calculator {


    public static final Logger logger = LogManager.getLogger();

    /**
     * Calculating order volume actualWeight in kg
     * Order dimensions data input in cm
     * Divisor 4000 it is a volume actualWeight divisor
     *
     * @param height       order height
     * @param length       order length
     * @param width        order width
     * @param actualWeight order actualWeight
     * @return double result of order volume weight
     */
    public static double calculateOrderVolumeWeight(double length, double height, double width, double actualWeight) {
        double volumeWeight;
        if (length == 0 || height == 0 || width == 0) {
            return 0;
        } else {
            volumeWeight = ((width * length * height) / 100) / 4000;
        }
        return Math.max(volumeWeight, actualWeight);
    }


    /**
     * Calculating order volume
     *
     * @param height order height
     * @param length order length
     * @param width  order width
     * @return double result of order volume
     */
    public static double calculateOrderVolume(double length, double height, double width) {
        if (length == 0 || height == 0 || width == 0) {
            return 0;
        } else return (width * length * height) / 100;
    }


    /**
     * Calculating delivery distance price by order type
     *
     * @param orderType        type of order
     * @param deliveryDistance delivery distance in km
     * @return delivery distance price
     */
    public static BigDecimal deliveryDistancePriceByOrderType(String orderType, double deliveryDistance) {

        BigDecimal distanceDeliveryPrice = null;

        switch (orderType) {
            case "DOCUMENT" -> {
                double documentDeliveryPricePerOneKm = 0.05;
                distanceDeliveryPrice = BigDecimal.valueOf(documentDeliveryPricePerOneKm * deliveryDistance);
            }
            case "PARCEL" -> {
                double parcelDeliveryPricePerOneKm = 0.13;
                distanceDeliveryPrice = BigDecimal.valueOf(parcelDeliveryPricePerOneKm * deliveryDistance);
            }
            case "CARGO" -> {
                double cargoDeliveryPricePerOneKm = 1.5;
                distanceDeliveryPrice = BigDecimal.valueOf(cargoDeliveryPricePerOneKm * deliveryDistance);
            }
        }
        return distanceDeliveryPrice;
    }

    /**
     * Calculating delivery price for document by actual weight in kg
     *
     * @param orderType        type of order
     * @param deliveryDistance delivery distance in km
     * @return delivery price of document
     */
    public static BigDecimal totalDocumentDeliveryPrice(String orderType, String deliveryDistance) {

        double deliveryDocumentPrice = 45;

        BigDecimal totalDeliveryDocumentPrice;

        double deliveryDistanceToDouble = Double.parseDouble(String.valueOf(deliveryDistance));

        BigDecimal distancePrice = deliveryDistancePriceByOrderType("DOCUMENT", deliveryDistanceToDouble);
        double distancePriceToDouble = Double.parseDouble(String.valueOf(distancePrice));

            totalDeliveryDocumentPrice = BigDecimal.valueOf(deliveryDocumentPrice + distancePriceToDouble);

        return totalDeliveryDocumentPrice;
    }

    /**
     * Calculating delivery price for parcel by volume weight in kg
     *
     * @param orderType        type of order
     * @param weight     order weight
     * @param height           order height
     * @param length           order length
     * @param width            order width
     * @param deliveryDistance delivery distance
     * @return delivery price of parcel
     */
    public static BigDecimal parcelDeliveryPrice(String orderType,
                                                 double weight,
                                                 double length,
                                                 double height,
                                                 double width,
                                                 double deliveryDistance) {
        BigDecimal deliveryParcelPrice = null;
        double parcelVolumeWeight = calculateOrderVolumeWeight(length, height, width, weight);
        BigDecimal distancePrice = deliveryDistancePriceByOrderType("PARCEL", deliveryDistance);
        double distancePriceToDouble = Double.parseDouble(String.valueOf(distancePrice));

        if (parcelVolumeWeight > 1 && parcelVolumeWeight <= 2) {
            deliveryParcelPrice = BigDecimal.valueOf(60 + distancePriceToDouble);
        }
        if (parcelVolumeWeight > 2 && parcelVolumeWeight <= 10) {
            deliveryParcelPrice = BigDecimal.valueOf(80 + distancePriceToDouble);
        }
        if (parcelVolumeWeight > 10 && parcelVolumeWeight <= 30) {
            deliveryParcelPrice = BigDecimal.valueOf(120 + distancePriceToDouble);
        }
        return deliveryParcelPrice;
    }


        /**
         * Calculating delivery price for cargo by volume weight in kg
         *
         * @param orderType        type of order
         * @param weight     order weight
         * @param height           order height
         * @param length           order length
         * @param width            order width
         * @param deliveryDistance delivery distance
         * @return delivery price of cargo
         */
    public static BigDecimal cargoDeliveryPrice(String orderType,
                                                double weight,
                                                double length,
                                                double height,
                                                double width,
                                                double deliveryDistance) {

        double cargoVolumeWeight = calculateOrderVolumeWeight(length, height, width, weight);

        BigDecimal distancePrice = deliveryDistancePriceByOrderType("CARGO", deliveryDistance);
        double distancePriceToDouble = Double.parseDouble(String.valueOf(distancePrice));

        BigDecimal deliveryCargoPrice = null;

            if (cargoVolumeWeight >= 31 && cargoVolumeWeight <= 249) {
                deliveryCargoPrice = BigDecimal.valueOf(130 + distancePriceToDouble);
            }
            if (cargoVolumeWeight >= 250 && cargoVolumeWeight <= 599) {
                deliveryCargoPrice = BigDecimal.valueOf(150 + distancePriceToDouble);
            }
            if (cargoVolumeWeight >= 599 && cargoVolumeWeight <= 1000) {
                deliveryCargoPrice = BigDecimal.valueOf(180 + distancePriceToDouble);
            }
        return deliveryCargoPrice;
    }


    /**
     * Calculating delivery price
     *
     * @param orderType        type of order
     * @param weight     order weight
     * @param height           order height
     * @param length           order length
     * @param width            order width
     * @param deliveryDistance delivery distance
     * @return delivery price
     */
    public static BigDecimal calculateDeliveryPrice(String orderType,
                                                    String weight,
                                                    String length,
                                                    String height,
                                                    String width,
                                                    String deliveryDistance) throws IncorrectInputException {

        BigDecimal totalDeliveryPrice = null;


        double actualWeightToDouble = Double.parseDouble(weight);
        double lengthToDouble = Double.parseDouble(length);
        double heightToDouble = Double.parseDouble(height);
        double widthToDouble = Double.parseDouble(width);
        double deliveryDistanceToDouble = Double.parseDouble(deliveryDistance);

        switch (orderType) {
            case "DOCUMENT" -> {
                double documentDeliveryDistancePrice = Double.parseDouble(String.valueOf(deliveryDistancePriceByOrderType("DOCUMENT", deliveryDistanceToDouble)));
                double deliveryDocumentPrice = Double.parseDouble(String.valueOf(totalDocumentDeliveryPrice("DOCUMENT", deliveryDistance)));
                totalDeliveryPrice = BigDecimal.valueOf(documentDeliveryDistancePrice + deliveryDocumentPrice);
            }
            case "PARCEL" -> {
                double parcelDeliveryDistancePrice = Double.parseDouble(String.valueOf(deliveryDistancePriceByOrderType("PARCEL", deliveryDistanceToDouble)));
                double deliveryParcelPrice = Double.parseDouble(String.valueOf(parcelDeliveryPrice("PARCEL", actualWeightToDouble, lengthToDouble, heightToDouble, widthToDouble, deliveryDistanceToDouble)));
                totalDeliveryPrice = BigDecimal.valueOf(parcelDeliveryDistancePrice + deliveryParcelPrice);
            }
            case "CARGO" -> {
                double cargoDeliveryDistancePrice = Double.parseDouble(String.valueOf(deliveryDistancePriceByOrderType("CARGO", deliveryDistanceToDouble)));
                double deliveryCargoPrice = Double.parseDouble(String.valueOf(cargoDeliveryPrice("CARGO", actualWeightToDouble, lengthToDouble, heightToDouble, widthToDouble, deliveryDistanceToDouble)));
                totalDeliveryPrice = BigDecimal.valueOf(cargoDeliveryDistancePrice + deliveryCargoPrice);
            }
        }
        return totalDeliveryPrice;
    }


    /**
     * Calculating total delivery service price for order
     *
     * @param orderPrice       order price
     * @param orderType        type of order from user input
     * @param weight           order weight
     * @param height           order height
     * @param length           order length
     * @param width            order width
     * @param deliveryDistance delivery distance
     * @return total price for all service
     */
    public static BigDecimal calculateTotalServiceDeliveryPrice(String orderType,
                                                                String orderPrice,
                                                                String weight,
                                                                String length,
                                                                String height,
                                                                String width,
                                                                String deliveryDistance) throws IncorrectInputException {
        BigDecimal totalDeliveryPrice;

        BigDecimal deliveryPrice = calculateDeliveryPrice(orderType, weight, length, height, width, deliveryDistance);

        double orderPriceToDouble = Double.parseDouble(orderPrice);
        double deliveryPriceToDouble = Double.parseDouble(String.valueOf(deliveryPrice));

        totalDeliveryPrice =  BigDecimal.valueOf(deliveryPriceToDouble + orderPriceToDouble);

        return totalDeliveryPrice;
    }

    /**
     * Define order type before execution calculations commands
     *
     * @param weight order weight
     * @param height order height
     * @param length order length
     * @param width  order width
     * @return correct type of order
     */
    public static String defineOrderType(
            String weight,
            String length,
            String height,
            String width) throws IncorrectInputException {

        String typeOfOrder = null;

        logger.log(Level.DEBUG, "Input height: " + height + "\t" +
                "Input length: " + length + "\t" +
                "Input width: " + width + "\t" +
                "Input weight: " + weight);

        double weightDouble = Double.parseDouble(weight);
        double lengthDouble = Double.parseDouble(length);
        double heightDouble = Double.parseDouble(height);
        double widthDouble = Double.parseDouble(width);

        double volumeWeight = calculateOrderVolumeWeight(lengthDouble, heightDouble, widthDouble, weightDouble);

        if (widthDouble <= 35 && lengthDouble <= 25 && heightDouble <= 2 && weightDouble <= 1) {
            typeOfOrder = "DOCUMENT";
        }
        if (volumeWeight > 1 && volumeWeight <= 30 || widthDouble > 35 && lengthDouble > 25 && heightDouble > 2 && weightDouble > 1) {
            typeOfOrder = "PARCEL";
        }
        if (volumeWeight > 30 && volumeWeight <= 1000) {
            typeOfOrder = "CARGO";
        }
        if (volumeWeight > 1000) {
            throw new IncorrectInputException("The entered weight value exceeded the permissible limits for the type of cargo: " + typeOfOrder);
        }
        return typeOfOrder;
    }


    /**
     * Get correct type before execution calculations commands
     *
     * @param orderType type of order from user input
     * @param weight order weight
     * @param height order height
     * @param length order length
     * @param width  order width
     * @return correct type of order
     */
    public static String getCorrectOrderType(String orderType,
                                             String weight,
                                             String length,
                                             String height,
                                             String width) throws IncorrectInputException {

        String correctOrder = defineOrderType(weight, length, height, width);

        if (orderType.equals(correctOrder)) {
            return orderType;
        } else {
            return correctOrder;
        }
    }


}



