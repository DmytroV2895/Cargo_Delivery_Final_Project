package com.varukha.webproject.command.impl.calculates;

import com.varukha.webproject.command.*;
import com.varukha.webproject.entity.Order;
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
 * The command using in order to provide total price fo delivering service
 *
 * @author Dmytro Varukha
 *
 */

public class CalculateDeliveryServiceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    public Order order;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);

        logger.log(Level.DEBUG, "Execute CalculateDeliveryServiceCommand");
        String deliveryDistance = request.getParameter(ParameterAndAttribute.DELIVERY_DISTANCE);
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderPrice = request.getParameter(ParameterAndAttribute.ORDER_PRICE);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);

        try {
            String typeOfOrder = Calculator.getCorrectOrderType(orderType, orderWeight, orderLength, orderHeight, orderWidth);
           if (orderType.equals(typeOfOrder)) {
               BigDecimal calculateTotalServiceDeliveryPrice = Calculator.calculateTotalServiceDeliveryPrice(orderType,
                       orderPrice,
                       orderWeight,
                       orderLength,
                       orderHeight,
                       orderWidth,
                       deliveryDistance);
               request.setAttribute("calculateTotalServiceDeliveryPrice", calculateTotalServiceDeliveryPrice);
               request.setAttribute(ParameterAndAttribute.MESSAGE, Message.CALCULATION_SUCCESS);
               logger.log(Level.DEBUG, "Try to get delivery service price: " + calculateTotalServiceDeliveryPrice.toString());
           } else {
               router.setPagePath(page);
           }
       } catch (IncorrectInputException e) {
           logger.log(Level.ERROR, "IncorrectInputException in getCorrectOrderType method " + e);
           router.setPagePath(PagePath.ERROR);
       }
        return router;
    }
}

