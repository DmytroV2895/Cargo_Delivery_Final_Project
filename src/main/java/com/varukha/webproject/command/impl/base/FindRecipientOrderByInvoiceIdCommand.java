package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.*;
import com.varukha.webproject.command.Router.Type;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.Invoice;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * The command find order by invoiceId
 *
 * @author Dmytro Varukha
 */


public class FindRecipientOrderByInvoiceIdCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing FindRecipientOrderByInvoiceIdCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        String invoiceId = request.getParameter(ParameterAndAttribute.ID_INVOICE);
        try {
            Optional<Invoice> invoice = invoiceService.getInvoiceById(Long.parseLong(invoiceId));
            if (invoice.isPresent()) {
                String page = request.getContextPath() + PagePath.TO_ORDER_INFO_PAGE;
                router.setPagePath(page);
                router.setType(Type.REDIRECT);
            } else {
                String page = request.getContextPath() + PagePath.TO_SERVICES_PAGE;
                router.setPagePath(page);
                router.setType(Type.REDIRECT);
                session.setAttribute(ParameterAndAttribute.MESSAGE, Message.GUEST_NO_ORDER_INFO);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Exception in InvoiceService in method execute");
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }

}
