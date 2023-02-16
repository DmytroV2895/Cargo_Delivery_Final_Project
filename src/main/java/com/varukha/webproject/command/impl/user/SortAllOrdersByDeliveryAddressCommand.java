package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.model.service.impl.InvoiceServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * The command sort all orders by first address
 *
 * @author Dmytro Varukha
 */


public class SortAllOrdersByDeliveryAddressCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl(ConnectionPool.getInstance()));

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
            List<Invoice> invoiceList = invoiceService.getAllSortedOrdersInfoFromInvoiceByDeliveryAddress(userId, deliveryCity, pageNumber);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_SORTED_USER_ORDERS_BY_DELIVERY_CITY);
            router.setPagePath(PagePath.SORTED_USER_ORDERS_BY_DELIVERY_CITY);
            request.setAttribute(ParameterAndAttribute.INVOICE_LIST, invoiceList);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in SortAllOrdersByDeliveryAddressCommand" + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
