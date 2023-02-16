package com.varukha.webproject.command.impl.routs;


import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.*;
import com.varukha.webproject.exception.ServiceException;
//import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
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

import java.util.List;


/**
 * Go to personal page command
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToPersonalPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = AppContext.getAppContext().getUserService();
    private final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToPersonalPageCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        long userId = user.getUserId();

            switch (user.getRole()) {
                case MANAGER -> {
                        logger.log(Level.DEBUG, "Case manger");
                        session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_PERSONAL_PAGE);
                        router.setPagePath(PagePath.PERSONAL_PAGE_MANAGER);
                }
                case USER -> {
                    try {
                        logger.log(Level.DEBUG, "Case user");
                        List<User> userById;
                        userById = userService.getUserById(userId);
                        session.setAttribute(ParameterAndAttribute.USER_BY_ID, userById);
                        session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_PERSONAL_PAGE);
                        router.setPagePath(PagePath.PERSONAL_PAGE_USER);
                    } catch (ServiceException e) {
                        logger.log(Level.ERROR, "ServiceException" + e);
                        session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
                        router.setPagePath(PagePath.ERROR);
                    }
                }
            }
        return router;
    }
}
