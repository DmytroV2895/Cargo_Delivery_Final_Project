package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.*;
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

import java.util.List;
import java.util.Optional;


/**
 *  Go to order invoice page in order to update delivery information (manager command)
 *
 * @author Dmytro Varukha
 *
 */



public class ToOrdersUpdatePageManagerCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl(ConnectionPool.getInstance()));

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "ToOrderUpdatePageManagerCommand");
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
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDER_UPDATE_PAGE_MANAGER);
                router.setPagePath(PagePath.ORDER_UPDATE_PAGE_MANAGER);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
                router.setPagePath(PagePath.ERROR);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Invoice ServiceException in getInvoiceById method");
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
















//
//package com.varukha.webproject.command.impl;
//
//        import com.varukha.webproject.command.*;
//        import com.varukha.webproject.entity.Invoice;
//        import com.varukha.webproject.exception.ServiceException;
//        import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
//        import com.varukha.webproject.model.service.InvoiceService;
//        import com.varukha.webproject.model.service.impl.InvoiceServiceImpl;
//        import jakarta.servlet.http.HttpServletRequest;
//        import jakarta.servlet.http.HttpSession;
//        import org.apache.logging.log4j.Level;
//        import org.apache.logging.log4j.LogManager;
//        import org.apache.logging.log4j.Logger;
//
//        import java.util.Optional;
//
//
///**
// *  Go to order update page manager command
// * @author Dmytro Varukha
// *
// */
//
//
//
//public class ToOrderUpdatePageManagerCommand implements Command {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl());
//
//    @Override
//    public Router execute(HttpServletRequest request) {
//        logger.log(Level.INFO, "ToOrderUpdatePageManagerCommand");
//        Router router = new Router();
//        HttpSession session = request.getSession();
//        long invoiceId = Long.parseLong(request.getParameter(ParameterAndAttribute.ID_INVOICE));
//
//        try {
//            Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(invoiceId);
//            logger.log(Level.DEBUG, "Invoice:" + optionalInvoice.get());
//            if (optionalInvoice.isPresent()) {
//                Invoice invoice = optionalInvoice.get();
//                request.setAttribute(ParameterAndAttribute.INVOICE, invoice);
//                logger.log(Level.DEBUG, "Invoice: " + invoice);
//                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDER_UPDATE_PAGE_MANAGER);
//                router.setPagePath(PagePath.ORDER_UPDATE_PAGE_MANAGER);
//            } else {
//                logger.log(Level.ERROR, "Nothing were found");
//                request.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, Message.UNKNOWN_PROBLEM);
//                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
//                router.setPagePath(PagePath.ERROR);
//            }
//        } catch (ServiceException e) {
//            logger.log(Level.ERROR, "Invoice ServiceException in getInvoiceById method");
//            request.setAttribute(ParameterAndAttribute.EXCEPTION, "ServiceException");
//            request.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, e);
//            router.setPagePath(PagePath.ERROR);
//        }
//        return router;
//    }
//}
//





