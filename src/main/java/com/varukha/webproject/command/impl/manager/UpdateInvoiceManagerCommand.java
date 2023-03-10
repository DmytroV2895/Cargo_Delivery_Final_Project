package com.varukha.webproject.command.impl.manager;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UpdateInvoiceManagerCommand it is a command type
 * that used to update delivery date and order status by manager and return rout to
 * orders' information manager page.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class UpdateInvoiceManagerCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    /**
     * Method execute use as start point of executing UpdateInvoiceManagerCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Execute UpdateInvoiceManagerCommand");
        Router router = new Router();
        Map<String, String> invoiceData = new HashMap<>();

        String invoiceId = request.getParameter(ParameterAndAttribute.ID_INVOICE);
        String deliveryDate = request.getParameter(ParameterAndAttribute.DELIVERY_DATE);
        String orderStatus = request.getParameter(ParameterAndAttribute.ORDER_STATUS);

        invoiceData.put(ParameterAndAttribute.ID_INVOICE, invoiceId);
        invoiceData.put(ParameterAndAttribute.DELIVERY_DATE, deliveryDate);
        invoiceData.put(ParameterAndAttribute.ORDER_STATUS, orderStatus);
        try {
            if (invoiceService.updateInvoiceDeliveryDateAndOrderStatus(invoiceData)) {
                String page = request.getContextPath() + PagePath.TO_ORDERS_MANAGER_PAGE;
                router.setPagePath(page);
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in updateInvoiceDeliveryDateAndOrderStatus method: " + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
