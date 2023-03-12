package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Class DefaultCommand it is a command type that returns route
 * to the main page when the application can not define the command type.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class DefaultCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing DefaultCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        router.setPagePath(PagePath.MAIN_PAGE);
        logger.log(Level.INFO, "Unknown command ");
        return router;
    }
}