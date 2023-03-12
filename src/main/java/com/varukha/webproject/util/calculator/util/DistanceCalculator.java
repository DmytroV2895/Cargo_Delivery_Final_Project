package com.varukha.webproject.util.calculator.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.varukha.webproject.util.calculator.util.CityCoordinates.*;

/**
 * Class DistanceCalculator used to calculate delivery distance between two cities.
 * <p>
 * In this code, first is defined the coordinates of the two cities as variables firstCityLatitude, firstCityLongitude,
 * secondCityLatitude, and secondCityLongitudeInRadians.
 * Then these values is converted from degrees to radians using the Math.toRadians() method.
 * Used to Haversine formula is calculated the distance between the two cities, which takes into account the
 * curvature of the Earth's surface. We assign the resulting distance in kilometers to the variable distance and
 */

public class DistanceCalculator {

    public static final Logger logger = LogManager.getLogger();
    public static final double RADIUS_OF_EARTH = 6371.0;

    /**
     * Method defineCity used to define city with necessary latitude and longitude from user request
     *
     * @param city name of city from user request
     * @return currentCity with definitely latitude and longitude
     */

    public static CityCoordinates defineCity(String city) {
        CityCoordinates currentCity = null;
        switch (city) {
            case "SUMY" -> {
                logger.log(Level.DEBUG, "City from request: " + city);
                currentCity = SUMY;
            }
            case "CHARKIV" -> {
                logger.log(Level.DEBUG, "City from request: " + city);
                currentCity = CHARKIV;
            }
            case "LVIV" -> {
                logger.log(Level.DEBUG, "City from request: " + city);
                currentCity = LVIV;
            }
            case "ODESSA" -> {
                logger.log(Level.DEBUG, "City from request: " + city);
                currentCity = ODESSA;
            }
        }
        return currentCity;
    }

    /**
     * Method calculateDistanceBetweenTwoCities used to calculate the distance between the two cities.
     *
     * @param firstCity  sender city
     * @param secondCity receiver city
     * @return deliveryDistance between two cities
     */
    public static double calculateDistanceBetweenTwoCities(String firstCity, String secondCity) {
        logger.log(Level.DEBUG, "Calculate distance between two cities. First city: " + firstCity + " Second city: " + secondCity);
        CityCoordinates cityFirst = defineCity(firstCity);
        CityCoordinates citySecond = defineCity(secondCity);
        double deliveryDistance;


        double firstCityLatitude = cityFirst.getLatitude();
        double firstCityLongitude = cityFirst.getLongitude();
        double secondCityLatitude = citySecond.getLatitude();
        double secondCityLongitude = citySecond.getLongitude();

        double firstCityLatitudeInRadians = Math.toRadians(firstCityLatitude);
        double firstCityLongitudeInRadians = Math.toRadians(firstCityLongitude);
        double secondCityLatitudeInRadians = Math.toRadians(secondCityLatitude);
        double secondCityLongitudeInRadians = Math.toRadians(secondCityLongitude);

        double dLat = secondCityLatitudeInRadians - firstCityLatitudeInRadians;
        double dLon = secondCityLongitudeInRadians - firstCityLongitudeInRadians;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(firstCityLatitudeInRadians) *
                Math.cos(secondCityLatitudeInRadians) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        deliveryDistance = RADIUS_OF_EARTH * c;
        logger.log(Level.DEBUG, "Distance between first city: " + firstCity +
                " and second city: " + secondCity + " is " + deliveryDistance);
        return Math.round(deliveryDistance);
    }
}



