package com.varukha.webproject.command.impl.base;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class SetLocaleCommand it is a command type that used to set locale in application
 * and returns route to current page after set locale operation.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class SetLocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing SetLocaleCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute SetLocaleCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(ParameterAndAttribute.CURRENT_PAGE);
        router.setPagePath(page);
        logger.log(Level.DEBUG, "language from page = " + request.getParameter(ParameterAndAttribute.LANGUAGE));
        session.setAttribute(ParameterAndAttribute.LOCALE, request.getParameter(ParameterAndAttribute.LANGUAGE));
        return router;
    }
}
