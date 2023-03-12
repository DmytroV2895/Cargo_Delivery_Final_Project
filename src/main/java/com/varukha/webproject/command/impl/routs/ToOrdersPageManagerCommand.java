package com.varukha.webproject.command.impl.routs;

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
 * Go to detailed order information page (manager command)
 *
 * @author Dmytro Varukha
 */

public class ToOrdersPageManagerCommand implements Command {

    private final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing ToOrdersPageManagerCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Executing ToOrderInfoPageManagerCommand");
        int numberOfPages;
        int startRow = 0;
        Router router = new Router();
        HttpSession session = request.getSession();

        try {
            List<Invoice>  invoices = invoiceService.getInvoicesFromPagePagination(startRow);
            numberOfPages = invoiceService.getNumberOfRecordsOfAllInvoices();
            if (!invoices.isEmpty()) {
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.ORDERS_MANAGER_PAGE);
                request.setAttribute(ParameterAndAttribute.INVOICE_LIST_MANAGER, invoices);
                session.setAttribute(ParameterAndAttribute.NUMBER_OF_PAGES, numberOfPages);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.ORDERS_MANAGER_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
