package com.varukha.webproject.command.impl.base;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class LogOutCommand it is a command type that used to log out user from the system
 * and returns route to main page after successful log out operation.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing LogOutCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute LogOutCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        router.setPagePath(PagePath.TO_MAIN_PAGE);
        return router;
    }
}
