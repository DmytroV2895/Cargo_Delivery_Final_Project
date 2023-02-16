package com.varukha.webproject.command.impl.calculates;

import com.varukha.webproject.command.*;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.Calculator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * The command using in order to provide price for delivery service
 *
 * @author Dmytro Varukha
 */


public class CalculateDeliveryPriceCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);

        logger.log(Level.DEBUG, "Execute method CalculateDeliveryPriceCommand");
        String deliveryDistance = request.getParameter(ParameterAndAttribute.DELIVERY_DISTANCE);
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);

        try {
            String typeOfOrder = Calculator.getCorrectOrderType(orderType, orderWeight, orderLength, orderHeight, orderWidth);
            if (orderType.equals(typeOfOrder)) {
                BigDecimal calculateDeliveryPrice = Calculator.calculateDeliveryPrice(orderType,
                        orderWeight,
                        orderLength,
                        orderHeight,
                        orderWidth,
                        deliveryDistance);
                request.setAttribute("calculateDeliveryPrice", calculateDeliveryPrice);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.CALCULATION_SUCCESS);
                logger.log(Level.DEBUG, "Try to get delivery price: " + calculateDeliveryPrice);
                router.setPagePath(page);
            } else {
                logger.log(Level.INFO, "IncorrectInputException in getCorrectOrderType method ");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_DATA_INPUT);
                router.setPagePath(page);
            }
        } catch (IncorrectInputException e) {
            logger.log(Level.ERROR, "IncorrectInputException in getCorrectOrderType method " + e);
            request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_WEIGHT_INPUT);
            router.setPagePath(page);
        }
        return router;
    }
}




