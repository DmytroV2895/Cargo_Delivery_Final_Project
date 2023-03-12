package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class ToNoAccessPage it is a command type that used to get
 * route to access denied page if user does not have permission.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToNoAccessPage implements Command {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing ToNoAccessPage.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToNoAccessPage");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_NO_ACCESS_PAGE);
        router.setPagePath(PagePath.NO_ACCESS_PAGE);
        return router;
    }
}
