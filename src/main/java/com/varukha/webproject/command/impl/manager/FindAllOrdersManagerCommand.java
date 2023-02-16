package com.varukha.webproject.command.impl.manager;


import com.varukha.webproject.command.*;
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
import java.util.List;



/**
 * The command FindAllOrdersManagerCommand used to find all users' orders
 * @author Dmytro Varukha
 * @version 1.0
 */
public class FindAllOrdersManagerCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing FindAllOrdersManagerCommand");
        List<Invoice> invoiceList;
        Router router = new Router();
        HttpSession session = request.getSession();
        int pageNumber = Integer.parseInt(request.getParameter(ParameterAndAttribute.START_FROM));
        logger.log(Level.DEBUG, "Page number:" + pageNumber);

        try {
            invoiceList = invoiceService.getInvoicesFromRow(pageNumber);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_MANAGER_PAGE);
            router.setPagePath(PagePath.ORDERS_MANAGER_PAGE);
            request.setAttribute(ParameterAndAttribute.INVOICE_LIST_MANAGER, invoiceList);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getInvoicesFromRow method" + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
