package com.varukha.webproject.model.service.validation;


import com.varukha.webproject.exception.IncorrectInputException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.varukha.webproject.util.Calculator.calculateOrderVolumeWeight;


/**
 * Class data validator
 *
 * @author Dmytro Varukha
 */

public class DataValidator {


    public static final Logger logger = LogManager.getLogger();


    /**
     * @param password
     * @return boolean result of checking password validation
     */

    public static boolean isPasswordValid(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return password.matches(ValidationConstants.PASSWORD_REGEX);
    }

    /**
     * @param name
     * @return boolean result of checking password validation
     */

    public static boolean isNameValid(String name) {
        logger.log(Level.DEBUG, "user name: " + name);
        if (name == null || name.isBlank()) {
            return false;
        }
        return name.matches(ValidationConstants.NAME_REGEX);
    }

    /**
     * @param number
     * @return boolean result of checking phone number validation
     */

    public static boolean isPhoneNumberValid(String number) {
        logger.log(Level.DEBUG, "phone number: " + number);
        if (number == null || number.isBlank()) {
            return false;
        }
        return number.matches(ValidationConstants.PHONE_NUMBER_REGEX);
    }

    /**
     * @param email
     * @return boolean result of checking email validation
     */
    public static boolean isEmailValid(String email) {
        logger.log(Level.DEBUG, "email: " + email);
        if (email == null || email.isBlank()) {
            return false;
        }
        return email.matches(ValidationConstants.EMAIL_REGEX);
    }


    /**
     * @param text
     * @return boolean result of checking text validation
     */
    public static boolean isTextValid(String text) throws IncorrectInputException {
        logger.log(Level.DEBUG, "text input: " + text);
        if (text == null || text.isBlank()) {
            throw new IncorrectInputException("Incorrect text data input");
        }
        return text.matches(ValidationConstants.ONLY_TEXT_REGEX);
    }

    /**
     * @param number
     * @return boolean result of checking number validation
     */
    public static boolean isNumberValid(String number) throws IncorrectInputException {
        logger.log(Level.DEBUG, "number input: " + number);
        if (number == null || number.isBlank()) {
            throw new IncorrectInputException("Incorrect number data input");
        }
        return number.matches(ValidationConstants.INTEGERS_REGEX);
    }


    /**
     * @param orderType
     * @return input orderType to html form
     */
    public static boolean verificationOrderType(String orderType, String weight, String length, String height, String width) {
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

        if (widthDouble <= 35 && lengthDouble <= 25 && heightDouble <= 2 && weightDouble <= 1) {
            typeOfOrder = "DOCUMENT";
        }
        if (volumeWeight > 1 && volumeWeight <= 30 || widthDouble > 35 && lengthDouble > 25 && heightDouble > 2 && weightDouble > 1) {
            typeOfOrder = "PARCEL";
        }
        if (volumeWeight > 30 && volumeWeight <= 1000) {
            typeOfOrder = "CARGO";
        }
        if (orderType.equals(typeOfOrder)) {
            isMatch = true;
        }
        return isMatch;
    }


    /**
     * Check all numbers for valid, including float numbers
     *
     * @param number input number from user
     * @return boolean result of operation
     */
    public static boolean isAllNumbersValid(String number) {
        logger.log(Level.DEBUG, "Number to validate " + number);
        if (number == null || number.isBlank()) {
            return false;
        }
        return number.matches(ValidationConstants.ALL_NUMBER_REGEX);
    }

}


//    switch (typeOfOrder) {
//            case "DOCUMENT" -> {
//            if (widthDouble <= 35 && lengthDouble <= 25 && heightDouble <= 2 && weightDouble <= 1) {
//            typeOfOrder = "DOCUMENT";
//            }
//            }
//            case "PARCEL" -> {
//            if (volumeWeight > 1 && volumeWeight <= 30 || widthDouble > 35 && lengthDouble > 25 && heightDouble > 2 && weightDouble > 1) {
//            typeOfOrder = "PARCEL";
//            }
//            }
//            case "CARGO" -> {
//            if (volumeWeight > 30 && volumeWeight <= 1000) {
//            typeOfOrder = "CARGO";
//            }
//            }
//            }