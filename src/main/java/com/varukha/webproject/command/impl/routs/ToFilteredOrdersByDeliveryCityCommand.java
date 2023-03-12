package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * Class ToFilteredOrdersByDeliveryCityCommand it is a command type
 * that used to get filtered by delivery city invoice data and return route to
 * sorted orders information page.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToFilteredOrdersByDeliveryCityCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    /**
     * Method execute use as start point of executing ToSortedOrdersByDeliveryCityCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Executing ToSortedOrdersByDeliveryCityCommand");
        int numberOfPages;
        int startRow = 0;
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        String deliveryCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);
        long userId = user.getUserId();
        try {
            List<Invoice> invoices = invoiceService.getAllFilteredOrdersInfoFromInvoiceByDeliveryCity(userId, deliveryCity, startRow);
            numberOfPages = invoiceService.getNumberOfRecordsInInvoiceTableByDeliveryCityPagination(userId, deliveryCity);
            if(!invoices.isEmpty()) {
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_SORTED_USER_ORDERS_BY_DELIVERY_CITY);
                router.setPagePath(PagePath.SORTED_USER_ORDERS_BY_DELIVERY_CITY);
                request.setAttribute(ParameterAndAttribute.INVOICE_LIST, invoices);
                session.setAttribute(ParameterAndAttribute.NUMBER_OF_PAGES, numberOfPages);


            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_SORTED_USER_ORDERS_BY_DELIVERY_CITY);
                router.setPagePath(PagePath.SORTED_USER_ORDERS_BY_DELIVERY_CITY);

            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception in ToSortedOrdersByDeliveryCityCommand" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
