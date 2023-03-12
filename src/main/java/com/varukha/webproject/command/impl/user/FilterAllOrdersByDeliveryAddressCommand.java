package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
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
 * Class FilterAllOrdersByDeliveryAddressCommand it is a command type
 * that used to find all filtered orders by delivery city and return route
 * to order information page for user with sorted data on the current page.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class FilterAllOrdersByDeliveryAddressCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    /**
     * Method execute use as start point of executing FilterAllOrdersByDeliveryAddressCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing SortAllOrdersByDeliveryAddressCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        long userId = user.getUserId();
        int pageNumber = Integer.parseInt(request.getParameter(ParameterAndAttribute.START_FROM));
        String deliveryCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);
        logger.log(Level.DEBUG, "Page number:" + pageNumber);
        try {
            List<Invoice> filteredInvoiceList = invoiceService.getAllFilteredOrdersInfoFromInvoiceByDeliveryCity(userId, deliveryCity, pageNumber);
            request.setAttribute(ParameterAndAttribute.INVOICE_LIST, filteredInvoiceList);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_SORTED_USER_ORDERS_BY_DELIVERY_CITY);
            router.setPagePath(PagePath.SORTED_USER_ORDERS_BY_DELIVERY_CITY);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in SortAllOrdersByDeliveryAddressCommand" + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
