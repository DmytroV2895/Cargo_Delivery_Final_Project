package com.varukha.webproject.command.impl.base;

import com.varukha.webproject.command.*;
import com.varukha.webproject.command.Router.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SignUpCommand it is a command type that used to sign up new user in the system
 * and returns route to main page after successful sign up operation.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = AppContext.getAppContext().getUserService();

    /**
     * Method execute use as start point of executing SignUpCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        Map<String, String> userData = new HashMap<>();
        logger.log(Level.DEBUG, "Execute SignUpCommand");
        String name = request.getParameter(ParameterAndAttribute.USER_NAME);
        String surname = request.getParameter(ParameterAndAttribute.USER_SURNAME);
        String email = request.getParameter(ParameterAndAttribute.USER_EMAIL);
        String password = request.getParameter(ParameterAndAttribute.USER_PASSWORD);
        String phone = request.getParameter(ParameterAndAttribute.USER_PHONE);
        String confirmedPassword = request.getParameter(ParameterAndAttribute.USER_CONFIRMED_PASSWORD);
        userData.put(ParameterAndAttribute.USER_NAME, name);
        userData.put(ParameterAndAttribute.USER_SURNAME, surname);
        userData.put(ParameterAndAttribute.USER_EMAIL, email);
        userData.put(ParameterAndAttribute.USER_PASSWORD, password);
        userData.put(ParameterAndAttribute.USER_PHONE, phone);
        if (password.equals(confirmedPassword)) {
            try {
                if (userService.getUserByEmail(email).isEmpty()) {
                    if (userService.addUser(userData)) {
                        String page = request.getContextPath();
                        router.setPagePath(page);
                        router.setType(Type.REDIRECT);
                        logger.log(Level.DEBUG, "User was added");
                    } else {
                        logger.log(Level.DEBUG, "User was not added");
                        router.setPagePath(PagePath.REGISTRATION_PAGE);
                        request.setAttribute(ParameterAndAttribute.MESSAGE, Message.USER_WAS_NOT_ADDED);
                    }
                } else {
                    logger.log(Level.INFO, "User already exist");
                    router.setPagePath(PagePath.REGISTRATION_PAGE);
                    request.setAttribute(ParameterAndAttribute.MESSAGE, Message.USER_ALREADY_EXIST);
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "ServiceException in SignUpCommand " + e);
                router.setPagePath(PagePath.ERROR);
            }
        } else {
            logger.log(Level.ERROR, "Password does not match");
            router.setPagePath(PagePath.REGISTRATION_PAGE);
            request.setAttribute(ParameterAndAttribute.MESSAGE, Message.PASSWORD_DOES_NOT_MATCH);
        }
        return router;
    }
}
