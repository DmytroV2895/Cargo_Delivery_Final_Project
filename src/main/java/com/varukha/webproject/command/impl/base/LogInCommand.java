package com.varukha.webproject.command.impl.base;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.UserService;
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
 * Class LogInCommand it is a command type that used to log in user to the system
 * and returns route to main page after successful log in operation.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class LogInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = AppContext.getAppContext().getUserService();

    /**
     * Method execute use as start point of executing LogInCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.DEBUG, "Executing  logIn command");
        Router router = new Router();
        HttpSession session = request.getSession();
        User userFromDB;
        String email = request.getParameter(ParameterAndAttribute.USER_EMAIL);
        String password = request.getParameter(ParameterAndAttribute.USER_PASSWORD);
        logger.log(Level.DEBUG, "User email: " + email + " User password: " + password);
        Optional<User> optionalUser;
        try {
            optionalUser = userService.getUserEmailPassword(email, password);
            if (optionalUser.isPresent()) {
                userFromDB = optionalUser.get();
                session.setAttribute(ParameterAndAttribute.USER, userFromDB);
                router.setPagePath(PagePath.TO_MAIN_PAGE);
            } else {
                router.setPagePath(PagePath.LOG_IN_PAGE);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_EMAIL_OR_PASSWORD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getUserEmailPassword method " + e);
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }

}