package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.*;
import com.varukha.webproject.util.validation.DataValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Class CreateNewOrderCommand it is a command type
 * that used to create new order to cargo delivery and return route
 * to personal page if operation was successful.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class CreateNewOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
    DeliveryService deliveryService = AppContext.getAppContext().getDeliveryService();
    OrderService orderService = AppContext.getAppContext().getOrderService();
    AddressFirstService addressFirstService = AppContext.getAppContext().getAddressFirstService();
    AddressSecondService addressSecondService = AppContext.getAppContext().getAddressSecondService();


    /**
     * Method execute use as start point of executing CreateNewOrderCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing CreateNewOrderCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> invoiceData = new HashMap<>();
        long addressFirstId;
        long addressSecondId;
        long deliveryId;
        long orderId;
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        String userId = Long.toString(user.getUserId());
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);

        try {
            boolean typeOfOrder = DataValidator.isOrderTypeValid(orderType, orderWeight, orderLength, orderHeight, orderWidth);
            if (typeOfOrder) {
                getDataFromRequest(request, invoiceData, userId, orderType, orderWeight, orderLength, orderHeight, orderWidth);
                addressFirstId = addressFirstService.addAddressFirst(invoiceData);
                invoiceData.put(ParameterAndAttribute.FIRST_ADDRESS_ID, String.valueOf(addressFirstId));

                addressSecondId = addressSecondService.addAddressSecond(invoiceData);
                invoiceData.put(ParameterAndAttribute.SECOND_ADDRESS_ID, String.valueOf(addressSecondId));

                deliveryId = deliveryService.addDelivery(invoiceData);
                invoiceData.put(ParameterAndAttribute.DELIVERY_ID, String.valueOf(deliveryId));

                orderId = orderService.addOrder(invoiceData);
                invoiceData.put(ParameterAndAttribute.ID_ORDER, String.valueOf(orderId));

                invoiceService.addInvoice(invoiceData);
                logger.log(Level.INFO, "Invoice data were updated successfully");
                redirectToPageWhenUpdateSuccess(request, router);
            }
        } catch (ServiceException | IncorrectInputException e) {
            logger.log(Level.ERROR, "IncorrectInputException in verificationOrderType method or ServiceException in update methods " + e);
            redirectToPageWhenUpdateFailed(request, router);
        }
        return router;
    }

    /**
     * Method redirectToPageWhenUpdateFailed used to go to the next page if the invoice updated is failed
     */
    private static void redirectToPageWhenUpdateFailed(HttpServletRequest request, Router router) {
        request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_ORDER_TYPE);
        String page = request.getContextPath() + PagePath.TO_PERSONAL_PAGE;
        router.setPagePath(page);
        router.setType(Router.Type.REDIRECT);
    }

    /**
     * Method redirectToPageWhenUpdateSuccess used to go to the next page if the invoice updated is successfully
     */
    private static void redirectToPageWhenUpdateSuccess(HttpServletRequest request, Router router) {
        request.setAttribute(ParameterAndAttribute.MESSAGE, Message.ORDER_WAS_CREATED);
        String page = request.getContextPath() + PagePath.TO_PERSONAL_PAGE;
        router.setPagePath(page);
        router.setType(Router.Type.REDIRECT);
    }

    /**
     * Method getDataFromRequest using in order to put invoice data from user request to Map, and use it in the
     * updateInvoice command.
     */
    private static void getDataFromRequest(HttpServletRequest request, Map<String, String> invoiceData, String userId,
                                           String orderType, String orderWeight, String orderLength, String orderHeight, String orderWidth) {
        logger.log(Level.DEBUG, "Invoice data in map from request: " + invoiceData);
        String orderName = request.getParameter(ParameterAndAttribute.ORDER_NAME);
        String orderPrice = request.getParameter(ParameterAndAttribute.ORDER_PRICE);
        String deliveryType = request.getParameter(ParameterAndAttribute.DELIVERY_TYPE);
        String deliveryDistance = request.getParameter(ParameterAndAttribute.DELIVERY_DISTANCE);
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
    }
}


