package com.varukha.webproject.util.validation;

import com.varukha.webproject.exception.IncorrectInputException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.varukha.webproject.util.calculator.util.CalculationUtil.calculateOrderVolumeWeight;


/**
 * Class DataValidator used to validation input data from user request.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public class DataValidator {

    public static final Logger logger = LogManager.getLogger();

    /**
     * Method isPasswordValid used to check the correctness of entering the password.
     *
     * @param password entered password.
     * @return boolean result of validation.
     */
    public static boolean isPasswordValid(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return password.matches(ValidationConstants.PASSWORD_REGEX);
    }

    /**
     * Method isNameValid used to check the correctness of entering the username.
     *
     * @param name entered username.
     * @return boolean result of validation.
     */
    public static boolean isNameValid(String name) {
        logger.log(Level.DEBUG, "User name: " + name);
        if (name == null || name.isBlank()) {
            return false;
        }
        return name.matches(ValidationConstants.NAME_REGEX);
    }

    /**
     * Method isPhoneNumberValid used to check the correctness of entering the phone number.
     *
     * @param phoneNumber entered phone number.
     * @return boolean result of validation.
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        logger.log(Level.DEBUG, "Phone number: " + phoneNumber);
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }
        return phoneNumber.matches(ValidationConstants.PHONE_NUMBER_REGEX);
    }

    /**
     * Method isEmailValid used to check the correctness of entering the user email.
     *
     * @param email entered user email.
     * @return boolean result of validation.
     */
    public static boolean isEmailValid(String email) {
        logger.log(Level.DEBUG, "Email: " + email);
        if (email == null || email.isBlank()) {
            return false;
        }
        return email.matches(ValidationConstants.EMAIL_REGEX);
    }

    /**
     * Method isTextValid used to check the correctness of entering the text.
     *
     * @param text entered text.
     * @return boolean result of validation.
     */
    public static boolean isTextValid(String text) throws IncorrectInputException {
        logger.log(Level.DEBUG, "text input: " + text);
        if (text == null || text.isBlank()) {
            throw new IncorrectInputException("Incorrect text data input");
        }
        return text.matches(ValidationConstants.ONLY_TEXT_REGEX);
    }

    /**
     * Method isNumberValid used to check the correctness of entering the numbers.
     *
     * @param number entered numbers.
     * @return boolean result of validation.
     */
    public static boolean isNumberValid(String number) throws IncorrectInputException {
        logger.log(Level.DEBUG, "number input: " + number);
        if (number == null || number.isBlank()) {
            throw new IncorrectInputException("Incorrect number data input");
        }
        return number.matches(ValidationConstants.INTEGERS_REGEX);
    }

    /**
     * Method isOrderTypeValid used to check the correctness of entering the order type.
     *
     * @param orderType type of order
     * @param height    order height
     * @param length    order length
     * @param width     order width
     * @return boolean result of validation.
     */
    public static boolean isOrderTypeValid(String orderType, String weight, String length, String height, String width) throws IncorrectInputException {
        String typeOfOrder = null;
        boolean isMatch = false;

        logger.log(Level.DEBUG, "Input height: " + height + "\t" +
                "Input length: " + length + "\t" +
                "Input width: " + width + "\t" +
                "Input weight: " + weight);
        double weightDouble = Double.parseDouble(weight);
        double lengthDouble = Double.parseDouble(length);
        double heightDouble = Double.parseDouble(height);
        double widthDouble = Double.parseDouble(width);

        double volumeWeight = calculateOrderVolumeWeight(lengthDouble, heightDouble, widthDouble, weightDouble);

        switch (orderType) {
            case "DOCUMENT" -> {
                boolean correctValues = widthDouble <= 35 && lengthDouble <= 25 && heightDouble <= 2 && weightDouble <= 1;
                if (correctValues) {
                    typeOfOrder = "DOCUMENT";
                }
                if (!correctValues) {
                    throw new IncorrectInputException("The entered weight value exceeded the permissible limits for the type of order: " + orderType);
                }
            }
            case "PARCEL" -> {
                boolean correctValues = volumeWeight > 1 && volumeWeight <= 30;
                if (correctValues) {
                    typeOfOrder = "PARCEL";
                }
                if (!correctValues) {
                    throw new IncorrectInputException("The entered weight value exceeded the permissible limits for the type of order: " + orderType);
                }
            }
            case "CARGO" -> {
                boolean correctValues = volumeWeight > 30 && volumeWeight <= 1000;
                if (correctValues) {
                    typeOfOrder = "CARGO";
                }
                if (!correctValues) {
                    throw new IncorrectInputException("The entered weight value exceeded the permissible limits for the type of order: " + typeOfOrder);
                }
            }
        }
        if (orderType.equals(typeOfOrder)) {
            isMatch = true;
        }
        return isMatch;
    }

    /**
     * Method isAllNumbersValid used to check the correctness of entering the of all numbers, including double type.
     *
     * @param number entered numbers.
     * @return boolean result of validation.
     */
    public static boolean isAllNumbersValid(String number) {
        logger.log(Level.DEBUG, "Numbers to validate " + number);
        if (number == null || number.isBlank()) {
            return false;
        }
        return number.matches(ValidationConstants.ALL_NUMBERS_REGEX);
    }


}

