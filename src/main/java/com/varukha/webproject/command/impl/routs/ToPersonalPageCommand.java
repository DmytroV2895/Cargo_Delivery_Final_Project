package com.varukha.webproject.command.impl.routs;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.entity.User;
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
 * Class ToPersonalPageCommand it is a command type that used to go to the personal page and
 * returns route to personal page depend on the role.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToPersonalPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = AppContext.getAppContext().getUserService();

    /**
     * Method execute use as start point of executing ToPersonalPageCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToPersonalPageCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        getUserByIdFromDB(request, router);
            switch (user.getRole()) {
                case USER -> {
                    logger.log(Level.DEBUG, "Case user");
                    session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_PERSONAL_PAGE);
                    router.setPagePath(PagePath.PERSONAL_PAGE_USER);
                }
                case MANAGER -> {
                    logger.log(Level.DEBUG, "Case manager");
                    session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_PERSONAL_PAGE);
                    router.setPagePath(PagePath.PERSONAL_PAGE_MANAGER);
                }
            }
        return router;
    }

    /**
     * Method getUserByIdFromDB used to get existed user from database by user id.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param router {@link Router} used to routing to specified application pages.
     */
    public void getUserByIdFromDB(HttpServletRequest request, Router router) {
        logger.log(Level.DEBUG, "Executing  getUserByIdFromDB method");
        HttpSession session = request.getSession();
        User userByUserFromDB = (User) session.getAttribute(ParameterAndAttribute.USER);
        Optional<User> optionalUserById;
        List<User> userByIdList;
        long userId = userByUserFromDB.getUserId();
        try {
            optionalUserById = userService.getUserById(userId);
            if (optionalUserById.isPresent()) {
                userByIdList = Converter.toList(optionalUserById);
                session.setAttribute(ParameterAndAttribute.USER_BY_ID, userByIdList);
            } else {
                router.setPagePath(PagePath.LOG_IN_PAGE);
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.USER_WAS_NOT_FOUND);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getUserByIdFromDB method" + e);
            session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ERROR);
            router.setPagePath(PagePath.ERROR);
        }
    }
}
