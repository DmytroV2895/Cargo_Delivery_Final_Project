//package com.varukha.webproject.model.service.validation;
//
//
//import com.varukha.webproject.entity.Order.Type;
//import com.varukha.webproject.exception.IncorrectInputException;
//import com.varukha.webproject.util.Calculator;
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.util.regex.Pattern;
//
///**
// * Class order validator
// * @author Dmytro Varukha
// *
// */
//
//public class OrderValidation {
//
//
//    public static final Logger logger = LogManager.getLogger();
//    private static Calculator calculator;
//
//    /**
//     * @param height
//     * @param length
//     * @param width
//     * @param orderType
//     * @return boolean result of order volume validation
//     */
//
//    public static boolean isOrderVolumeWeightValid(String height, String length, String width, String actualWeight, Type orderType) throws IncorrectInputException {
//        boolean isValid = true;
//        logger.log(Level.DEBUG, "Input height: " + height + "\t" +
//                                        "Input length: " + length + "\t" +
//                                        "Input width: " + width + "\t" +
//                                        "Input weight: " + actualWeight);
//        if (height == null || length == null || width == null || orderType == null ||
//                !Pattern.compile(ValidationConstants.ORDER_REGEX).matcher(height).matches() ||
//                !Pattern.compile(ValidationConstants.ORDER_REGEX).matcher(length).matches() ||
//                !Pattern.compile(ValidationConstants.ORDER_REGEX).matcher(width).matches()){
//            isValid = false;
//            throw new IncorrectInputException("Incorrect entry of order dimensions");
//        }
//        logger.log(Level.DEBUG, "Input height: " + height + "\t" +
//                                        "Input length: " + length + "\t" +
//                                        "Input width: " + width + "\t" +
//                                        "Input weight: " + actualWeight);
//        double widthDouble = Double.parseDouble(width);
//        double lengthDouble = Double.parseDouble(length);
//        double heightDouble = Double.parseDouble(height);
//        double actualWeightDouble = Double.parseDouble(actualWeight);
//        double volumeWeight = calculator.calculateOrderVolumeWeight(widthDouble, lengthDouble, heightDouble, actualWeightDouble);
//
//        if ((widthDouble > 35 && lengthDouble > 25 && heightDouble > 2 && actualWeightDouble > 1 && orderType.equals(Type.DOCUMENT) ||
//            (volumeWeight > 30 && orderType.equals(Type.PARCEL)) ||
//            (volumeWeight > 1000 && orderType.equals(Type.CARGO)))) {
//            isValid = false;
//            throw new IncorrectInputException("Volume weight exceed the permissible values for this order");
//        }
//        return isValid;
//    }
//}
