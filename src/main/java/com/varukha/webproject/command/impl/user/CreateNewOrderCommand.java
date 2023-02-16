package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.*;
import com.varukha.webproject.util.Calculator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Create new order command
 * @author Dmytro Varukha
 * @version 1.0
 *
 */



public class CreateNewOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
    DeliveryService deliveryService = AppContext.getAppContext().getDeliveryService();
    OrderService orderService = AppContext.getAppContext().getOrderService();
    AddressFirstService addressFirstService = AppContext.getAppContext().getAddressFirstService();
    AddressSecondService addressSecondService = AppContext.getAppContext().getAddressSecondService();


    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing CreateNewOrderCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> invoiceData = new HashMap<>();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        String page = request.getContextPath() + PagePath.TO_PERSONAL_PAGE;

        String userId = Long.toString(user.getUserId());
        String orderName = request.getParameter(ParameterAndAttribute.ORDER_NAME);
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderPrice = request.getParameter(ParameterAndAttribute.ORDER_PRICE);
        String deliveryType = request.getParameter(ParameterAndAttribute.DELIVERY_TYPE);
        String deliveryDistance = request.getParameter(ParameterAndAttribute.DELIVERY_DISTANCE);
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);
        String orderDescription = request.getParameter(ParameterAndAttribute.ORDER_DESCRIPTION);
        String recipientName = request.getParameter(ParameterAndAttribute.RECIPIENT_NAME);
        String recipientSurname = request.getParameter(ParameterAndAttribute.RECIPIENT_SURNAME);
        String recipientPhone = request.getParameter(ParameterAndAttribute.RECIPIENT_PHONE);
        String firstCity = request.getParameter(ParameterAndAttribute.FIRST_CITY);
        String secondCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);
        String first_street_name = request.getParameter(ParameterAndAttribute.FIRST_STREET_NAME);
        String second_street_name = request.getParameter(ParameterAndAttribute.SECOND_STREET_NAME);
        String first_street_number = request.getParameter(ParameterAndAttribute.FIRST_STREET_NUMBER);
        String second_street_number = request.getParameter(ParameterAndAttribute.SECOND_STREET_NUMBER);
        String first_house_number = request.getParameter(ParameterAndAttribute.FIRST_HOUSE_NUMBER);
        String second_house_number = request.getParameter(ParameterAndAttribute.SECOND_HOUSE_NUMBER);

        invoiceData.put(ParameterAndAttribute.USER_ID, userId);
        invoiceData.put(ParameterAndAttribute.ORDER_NAME, orderName);
        invoiceData.put(ParameterAndAttribute.ORDER_TYPE, orderType);
        invoiceData.put(ParameterAndAttribute.ORDER_PRICE, orderPrice);
        invoiceData.put(ParameterAndAttribute.DELIVERY_TYPE, deliveryType);
        invoiceData.put(ParameterAndAttribute.DELIVERY_DISTANCE, deliveryDistance);
        invoiceData.put(ParameterAndAttribute.ORDER_WEIGHT, orderWeight);
        invoiceData.put(ParameterAndAttribute.ORDER_LENGTH, orderLength);
        invoiceData.put(ParameterAndAttribute.ORDER_HEIGHT, orderHeight);
        invoiceData.put(ParameterAndAttribute.ORDER_WIDTH, orderWidth);
        invoiceData.put(ParameterAndAttribute.ORDER_DESCRIPTION, orderDescription);
        invoiceData.put(ParameterAndAttribute.RECIPIENT_NAME, recipientName);
        invoiceData.put(ParameterAndAttribute.RECIPIENT_SURNAME, recipientSurname);
        invoiceData.put(ParameterAndAttribute.RECIPIENT_PHONE, recipientPhone);
        invoiceData.put(ParameterAndAttribute.FIRST_CITY, firstCity);
        invoiceData.put(ParameterAndAttribute.SECOND_CITY, secondCity);
        invoiceData.put(ParameterAndAttribute.FIRST_STREET_NAME, first_street_name);
        invoiceData.put(ParameterAndAttribute.SECOND_STREET_NAME, second_street_name);
        invoiceData.put(ParameterAndAttribute.FIRST_STREET_NUMBER, first_street_number);
        invoiceData.put(ParameterAndAttribute.SECOND_STREET_NUMBER, second_street_number);
        invoiceData.put(ParameterAndAttribute.FIRST_HOUSE_NUMBER, first_house_number);
        invoiceData.put(ParameterAndAttribute.SECOND_HOUSE_NUMBER, second_house_number);
        logger.log(Level.DEBUG, "Invoice data in map from request: " + invoiceData);
        try {
            String typeOfOrder = Calculator.getCorrectOrderType(orderType, orderWeight, orderLength, orderHeight, orderWidth);
            if (orderType.equals(typeOfOrder)) {

                long addressFirstId = addressFirstService.addAddressFirst(invoiceData);
                invoiceData.put(ParameterAndAttribute.FIRST_ADDRESS_ID, String.valueOf(addressFirstId));
                long addressSecondId = addressSecondService.addAddressSecond(invoiceData);
                invoiceData.put(ParameterAndAttribute.SECOND_ADDRESS_ID, String.valueOf(addressSecondId));
                long deliveryId = deliveryService.addDelivery(invoiceData);
                invoiceData.put(ParameterAndAttribute.DELIVERY_ID, String.valueOf(deliveryId));
                long orderId = orderService.addOrder(invoiceData);
                invoiceData.put(ParameterAndAttribute.ID_ORDER, String.valueOf(orderId));

                invoiceService.addInvoice(invoiceData);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.ORDER_WAS_CREATED);
                router.setPagePath(page);
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException | IncorrectInputException e) {
            logger.log(Level.ERROR, "Exception in CreateNewOrderCommand methods " + e);
            request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_ORDER_TYPE);
            router.setPagePath(page);
            router.setType(Router.Type.REDIRECT);
        }
        return router;
    }
}


