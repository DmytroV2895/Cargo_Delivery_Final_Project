package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.Converter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

/**
 * Class ToOrderInfoPageUserCommand it is a command type that used to get definite
 * invoice data by invoiceId and returns route to order information page for user if such information was found.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToOrderInfoPageUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    /**
     * Method execute use as start point of executing ToOrderInfoPageUserCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToOrderInfoPageUserCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        long invoiceId = Long.parseLong(request.getParameter(ParameterAndAttribute.ID_INVOICE));

        try {
            Optional<Invoice> invoiceById = invoiceService.getInvoiceById(invoiceId);
            logger.log(Level.DEBUG, "Invoice: " + invoiceById);
            if (invoiceById.isPresent()) {
                List<Invoice> invoiceListById = Converter.toList(invoiceById);
                request.setAttribute(ParameterAndAttribute.INVOICE_BY_ID, invoiceListById);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDER_INFO_PAGE);
                router.setPagePath(PagePath.ORDER_INFO_PAGE);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
                router.setPagePath(PagePath.ERROR);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Invoice ServiceException in getInvoiceById method", e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }

}
