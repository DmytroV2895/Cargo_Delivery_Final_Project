package com.varukha.webproject.command.impl.manager;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.Invoice;
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
 * Class FindAllOrdersManagerCommand it is a command type that used to get all
 * orders' information of all users and returns route to manager order information page that
 * display that records on definite page.
 * @author Dmytro Varukha
 * @version 1.0
 */
public class FindAllOrdersManagerCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    /**
     * Method execute use as start point of executing FindAllOrdersManagerCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing FindAllOrdersManagerCommand");
        List<Invoice> invoiceList;
        Router router = new Router();
        HttpSession session = request.getSession();
        int pageNumber = Integer.parseInt(request.getParameter(ParameterAndAttribute.START_FROM));
        logger.log(Level.DEBUG, "Page number:" + pageNumber);
        try {
            invoiceList = invoiceService.getInvoicesFromPagePagination(pageNumber);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_MANAGER_PAGE);
            router.setPagePath(PagePath.ORDERS_MANAGER_PAGE);
            request.setAttribute(ParameterAndAttribute.INVOICE_LIST_MANAGER, invoiceList);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getInvoicesFromPagePagination method" + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
