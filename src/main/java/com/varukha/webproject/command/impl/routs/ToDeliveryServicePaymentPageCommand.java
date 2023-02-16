package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.model.service.impl.InvoiceServiceImpl;
import com.varukha.webproject.util.Converter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 *  Go to delivery service payment page command
 * @author Dmytro Varukha
 * @version 1.0
 *
 */

public class ToDeliveryServicePaymentPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToDeliveryServicePaymentPageCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        long invoiceId = Long.parseLong(request.getParameter(ParameterAndAttribute.ID_INVOICE));

        try {
            Optional<Invoice> invoiceById = invoiceService.getInvoiceById(invoiceId);
            logger.log(Level.DEBUG, "Invoice:" + invoiceById);
            if (invoiceById.isPresent()) {
                List<Invoice> invoiceListById = Converter.toList(invoiceById);
                request.setAttribute(ParameterAndAttribute.INVOICE_BY_ID, invoiceListById);
                logger.log(Level.DEBUG, "Invoice: " + invoiceListById);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_DELIVERY_PAYMENT_PAGE);
                router.setPagePath(PagePath.DELIVERY_PAYMENT_PAGE);

            } else {
                logger.log(Level.ERROR, "Invoice was not found");
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
                router.setPagePath(PagePath.ERROR);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Invoice ServiceException ToDeliveryServicePaymentPageCommand", e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }

}



