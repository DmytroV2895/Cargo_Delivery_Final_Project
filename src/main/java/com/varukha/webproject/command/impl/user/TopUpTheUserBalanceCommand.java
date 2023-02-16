package com.varukha.webproject.command.impl.user;


import com.varukha.webproject.command.*;
import com.varukha.webproject.command.Router.Type;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.UserDAOImpl;
import com.varukha.webproject.model.service.UserService;
import com.varukha.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;



/**
 * The command using in order to top up user balance
 * @author Dmytro Varukha
 * @version 1.0
 */


public class TopUpTheUserBalanceCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    UserService userService = AppContext.getAppContext().getUserService();

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
