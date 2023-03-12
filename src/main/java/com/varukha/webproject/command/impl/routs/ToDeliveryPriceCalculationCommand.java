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
 * Class ToDeliveryPriceCalculationCommand it is a command type
 * that used to get route to delivery price calculation page.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ToDeliveryPriceCalculationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Method execute use as start point of executing ToDeliveryPriceCalculationCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute ToDeliveryPriceCalculationCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_DELIVERY_PRICE_CALCULATION_PAGE);
        router.setPagePath(PagePath.DELIVERY_PRICE_CALCULATION_PAGE);
        return router;
    }
}
