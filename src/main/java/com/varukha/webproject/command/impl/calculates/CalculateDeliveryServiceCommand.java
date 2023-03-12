package com.varukha.webproject.command.impl.calculates;

import com.varukha.webproject.command.*;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.util.calculator.impl.TotalDeliveryPriceCalculator;
import com.varukha.webproject.util.validation.DataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import static com.varukha.webproject.command.ParameterAndAttribute.CALCULATE_TOTAL_DELIVERY_PRICE;

/**
 * Class CalculateDeliveryServiceCommand it is a command type that used to calculate delivery service price
 * and returns route to current calculation page after successful operation.
 * @author Dmytro Varukha
 * @version 1.0
 */
public class CalculateDeliveryServiceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    /**
     * Method execute use as start point of executing CalculateDeliveryServiceCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
        logger.log(Level.DEBUG, "Execute CalculateDeliveryServiceCommand");
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderPrice = request.getParameter(ParameterAndAttribute.ORDER_PRICE);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);
        String firstCity = request.getParameter(ParameterAndAttribute.FIRST_CITY);
        String secondCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);
        String deliveryType = request.getParameter(ParameterAndAttribute.DELIVERY_TYPE);
        try {
            boolean typeOfOrder = DataValidator.isOrderTypeValid(orderType, orderWeight, orderLength, orderHeight, orderWidth);
            if (typeOfOrder) {
                BigDecimal calculateTotalServiceDeliveryPrice = TotalDeliveryPriceCalculator.calculateTotalPriceOfDeliveryService(orderType,
                        orderPrice, orderWeight, orderLength, orderHeight, orderWidth, firstCity, secondCity, deliveryType);
                request.setAttribute(CALCULATE_TOTAL_DELIVERY_PRICE, calculateTotalServiceDeliveryPrice);
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

