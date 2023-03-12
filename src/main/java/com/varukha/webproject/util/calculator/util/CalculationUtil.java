package com.varukha.webproject.util.calculator.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class CalculationUtil contain a trivial calculation methods that used to calculate delivery order price.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class CalculationUtil {

    public static final Logger logger = LogManager.getLogger();

    /**
     * Method calculateOrderVolumeWeight used to calculate order
     * volume weight for order types such as Parcel and Cargo in kg.
     * Order dimensions data input in cm.
     * Divisor 4000 it is a volume weight divisor.
     *
     * @param height order height.
     * @param length order length.
     * @param width  order width.
     * @param weight order weight.
     * @return max value between volume weight and order weight.
     */
    public static double calculateOrderVolumeWeight(double length, double height, double width, double weight) {
        logger.log(Level.DEBUG, "Calculate order volume weight. Length: " + length +
                " height: " + height + " width: " + width + " weight: " + weight);
        double volumeWeight;
        if (length == 0 || height == 0 || width == 0) {
            return 0;
        } else {
            volumeWeight = ((width * length * height) / 100) / 4000;
            logger.log(Level.DEBUG, "Calculated volume weight. " + volumeWeight);
        }
        return Math.max(volumeWeight, weight);
    }

    /**
     * Method calculateOrderVolume user to calculate order volume.
     *
     * @param height order height.
     * @param length order length.
     * @param width  order width.
     * @return order volume
     */
    public static double calculateOrderVolume(double length, double height, double width) {
        logger.log(Level.DEBUG, "Calculate order volume. Length: " + length +
                " height: " + height + " width: " + width);
        if (length == 0 || height == 0 || width == 0) {
            return 0;
        } else return (width * length * height) / 100;
    }

}
