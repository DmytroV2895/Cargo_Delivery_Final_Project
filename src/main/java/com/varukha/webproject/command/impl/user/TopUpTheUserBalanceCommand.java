package com.varukha.webproject.command.impl.user;

import com.varukha.webproject.command.*;
import com.varukha.webproject.command.Router.Type;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class TopUpTheUserBalanceCommand it is a command type
 * that used to top up the account balance in order to provide payment operation
 * of delivery service and return route to user personal page if operation was successful.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class TopUpTheUserBalanceCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    UserService userService = AppContext.getAppContext().getUserService();

    /**
     * Method execute use as start point of executing TopUpTheUserBalanceCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Execute TopUpTheUserBalanceCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        boolean isAdded;
        long userId = user.getUserId();
        String money = request.getParameter(ParameterAndAttribute.USER_ACCOUNT_MONEY_SUM);
        try {
            isAdded = userService.addMoneyToUserAccount(userId, money);
            String page = request.getContextPath() + PagePath.TO_PERSONAL_PAGE;
            if (isAdded) {
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.ADD_MONEY_SUCCESSFUL);
                router.setPagePath(page);
                router.setType(Type.REDIRECT);
            } else {
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.ADD_MONEY_NOT_SUCCESSFUL);
                router.setPagePath(page);
                router.setType(Type.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in addMoneyToUserAccount method: " + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}
