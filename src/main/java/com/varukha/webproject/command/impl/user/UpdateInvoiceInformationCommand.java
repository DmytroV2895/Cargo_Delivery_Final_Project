package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.varukha.webproject.util.validation.DataValidator.isOrderTypeValid;


/**
 * Class UpdateInvoiceInformationCommand it is a command type
 * that used to update order data by user return route
 * to user personal page if operation was successful.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class UpdateInvoiceInformationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
   private final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
    private final DeliveryService deliveryService = AppContext.getAppContext().getDeliveryService();
    private final OrderService orderService = AppContext.getAppContext().getOrderService();
    private final AddressFirstService addressFirstService = AppContext.getAppContext().getAddressFirstService();
    private final AddressSecondService addressSecondService = AppContext.getAppContext().getAddressSecondService();

    /**
     * Method execute use as start point of executing UpdateInvoiceInformationCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Execute method UpdateInvoiceInformationCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> invoiceData = new HashMap<>();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);

        String userId = Long.toString(user.getUserId());
        String orderType = request.getParameter(ParameterAndAttribute.ORDER_TYPE);
        String orderWeight = request.getParameter(ParameterAndAttribute.ORDER_WEIGHT);
        String orderLength = request.getParameter(ParameterAndAttribute.ORDER_LENGTH);
        String orderHeight = request.getParameter(ParameterAndAttribute.ORDER_HEIGHT);
        String orderWidth = request.getParameter(ParameterAndAttribute.ORDER_WIDTH);

        try {
            boolean typeOfOrder = isOrderTypeValid(orderType, orderWeight, orderLength, orderHeight, orderWidth);
            if (typeOfOrder) {
                getDataFromRequest(request, invoiceData, userId, orderType, orderWeight, orderLength, orderHeight, orderWidth);

                addressFirstService.updateAddressFirst(invoiceData);
                addressSecondService.updateAddressSecond(invoiceData);
                deliveryService.updateDelivery(invoiceData);
                orderService.updateOrder(invoiceData);
                invoiceService.updateInvoice(invoiceData);

                logger.log(Level.INFO, "Invoice data were updated successfully");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.ORDER_WAS_UPDATED);
                redirectToPage(request, router);
            } else {
                logger.log(Level.ERROR, "IncorrectInputException in verificationOrderType method ");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_ORDER_TYPE);
                redirectToPage(request, router);
            }
        } catch (ServiceException | IncorrectInputException e) {
            logger.log(Level.ERROR, "IncorrectInputException in verificationOrderType method or ServiceException in update methods " + e);
            redirectToPage(request, router);
        }
        return router;
    }

    /**
     * Method redirectToPageWhenUpdateFailed used to go to the next page if the invoice updated is failed
     */
    private static void redirectToPage(HttpServletRequest request, Router router) {
        String page = request.getContextPath() + PagePath.TO_ORDERS_USER_PAGE;
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
        String invoiceId = request.getParameter(ParameterAndAttribute.ID_INVOICE);
        String addressFirstId = request.getParameter(ParameterAndAttribute.FIRST_ADDRESS_ID);
        String addressSecondId = request.getParameter(ParameterAndAttribute.SECOND_ADDRESS_ID);
        String orderId = request.getParameter(ParameterAndAttribute.ID_ORDER);
        String deliveryId = request.getParameter(ParameterAndAttribute.DELIVERY_ID);
        String orderName = request.getParameter(ParameterAndAttribute.ORDER_NAME);
        String orderPrice = request.getParameter(ParameterAndAttribute.ORDER_PRICE);
        String deliveryType = request.getParameter(ParameterAndAttribute.DELIVERY_TYPE);

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

        invoiceData.put(ParameterAndAttribute.ID_INVOICE, invoiceId);
        invoiceData.put(ParameterAndAttribute.FIRST_ADDRESS_ID, addressFirstId);
        invoiceData.put(ParameterAndAttribute.SECOND_ADDRESS_ID, addressSecondId);
        invoiceData.put(ParameterAndAttribute.DELIVERY_ID, deliveryId);
        invoiceData.put(ParameterAndAttribute.ID_ORDER, orderId);
        invoiceData.put(ParameterAndAttribute.USER_ID, userId);
        invoiceData.put(ParameterAndAttribute.ORDER_NAME, orderName);
        invoiceData.put(ParameterAndAttribute.ORDER_TYPE, orderType);
        invoiceData.put(ParameterAndAttribute.ORDER_PRICE, orderPrice);
        invoiceData.put(ParameterAndAttribute.DELIVERY_TYPE, deliveryType);

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

