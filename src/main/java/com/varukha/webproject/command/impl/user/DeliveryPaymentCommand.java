package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.*;
import com.varukha.webproject.model.service.*;
import com.varukha.webproject.model.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Payment delivery service command
 * @author Dmytro Varukha
 * @version 1.0
 */


public class DeliveryPaymentCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
    UserService userService = AppContext.getAppContext().getUserService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Execute DeliveryPaymentCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);

        long userId = user.getUserId();
        String totalPrice = request.getParameter(ParameterAndAttribute.TOTAL_PRICE);
        String invoiceId = request.getParameter(ParameterAndAttribute.ID_INVOICE);
        logger.log(Level.DEBUG, "Providing payment operation. Delivery price: " + totalPrice + ". OrderId: " + invoiceId);

        try {
            if (userService.payForDeliveryService(totalPrice, userId)) {
                invoiceService.updateDeliveryPaymentStatusByInvoiceId(Long.parseLong(invoiceId));
                String page = request.getContextPath() + PagePath.TO_PERSONAL_PAGE;
                router.setPagePath(page);
                router.setType(Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException: " + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}


