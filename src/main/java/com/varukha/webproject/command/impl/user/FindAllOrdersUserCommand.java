package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.entity.User;
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
 * The command to find all orders (user command)
 * @author Dmytro Varukha
 * @version 1.0
 */

public class FindAllOrdersUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing FindAllOrdersUserCommand");
        List<Invoice> invoiceList;
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        long userId = user.getUserId();
        int pageNumber = Integer.parseInt(request.getParameter(ParameterAndAttribute.START_FROM));
        logger.log(Level.DEBUG, "Page number:" + pageNumber);

        try {
            invoiceList = invoiceService.getAllOrdersInfoFromInvoiceByUserId(userId, pageNumber);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_USER_PAGE);
            router.setPagePath(PagePath.ORDERS_USER_PAGE);
            request.setAttribute(ParameterAndAttribute.INVOICE_LIST, invoiceList);

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in FindAllOrdersUserCommand" + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
