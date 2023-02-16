package com.varukha.webproject.controller.filters;


import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.entity.User;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.varukha.webproject.command.PagePath.*;


/**
 * AuthorizationFilter class. Controls access to pages
 * @author Dmytro Varukha
 * @version 1.0
 */


@WebFilter(filterName = "AccessFilter", dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD}, urlPatterns = "*.jsp")
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final Set<String> ALLOWED_PATH_GUEST = new HashSet<>(
            Arrays.asList(
                    INDEX,
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    PRICE_CALCULATION_PAGE,
                    ERROR,
                    TARIFFS_PAGE,
                    DELIVERY_SERVICE_PAGE,
                    ORDER_INFO_PAGE,
                    ORDER_INFO_PAGE_GUEST,
                    NO_ACCESS_PAGE,
                    HEADER
            ));

    private static final Set<String> ALLOWED_PATH_USER = new HashSet<>(
            Arrays.asList(
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    PRICE_CALCULATION_PAGE,
                    ERROR,
                    TARIFFS_PAGE,
                    DELIVERY_SERVICE_PAGE,
                    ORDER_INFO_PAGE,
                    ORDER_INFO_PAGE_GUEST,
                    NO_ACCESS_PAGE,
                    HEADER,

                    PERSONAL_PAGE_USER,
                    ORDER_UPDATE_PAGE_USER,
                    CREATE_ORDER_PAGE,
                    ORDERS_USER_PAGE,
                    DELIVERY_PAYMENT_PAGE,
                    SORTED_USER_ORDERS_BY_DELIVERY_CITY
            ));
    private static final Set<String> ALLOWED_PATH_MANAGER = new HashSet<>(
            Arrays.asList(
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    PRICE_CALCULATION_PAGE,
                    ERROR,
                    TARIFFS_PAGE,
                    DELIVERY_SERVICE_PAGE,
                    ORDER_INFO_PAGE_GUEST,
                    NO_ACCESS_PAGE,
                    HEADER,

                    PERSONAL_PAGE_MANAGER,
                    BILL_PAGE,
                    ORDERS_MANAGER_PAGE,
                    ORDER_UPDATE_PAGE_MANAGER

            ));


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.log(Level.INFO, "====> Start Authentication filter");
        HttpServletRequest req = (HttpServletRequest) request;
        String pagePath = req.getServletPath();
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(ParameterAndAttribute.USER);

        boolean allowedPathGuest = ALLOWED_PATH_GUEST.contains(pagePath);
        boolean allowedPathUser = ALLOWED_PATH_USER.contains(pagePath);
        boolean allowedPathManager = ALLOWED_PATH_MANAGER.contains(pagePath);

        if (user != null) {
            switch (user.getRole()) {
                case USER -> {
                    if (allowedPathUser) {
                        logger.log(Level.INFO, "====> Authentication filter. User has permission");
                        chain.doFilter(request, response);
                    } else {
                        logger.log(Level.INFO, "====> Authentication filter. User has no permission");
                        resp.sendRedirect(req.getContextPath() + TO_NO_ACCESS_PAGE);
                    }
                }
                case MANAGER -> {
                    if (allowedPathManager) {
                        logger.log(Level.INFO, "====> Authentication filter for manager");
                        chain.doFilter(request, response);
                    }
                }
            }
        } else {
            if (allowedPathGuest) {
                logger.log(Level.INFO, "====> Authentication filter. Default permission");
                chain.doFilter(request, response);
            } else {
                logger.log(Level.INFO, "====> Authentication filter. Guest has no permission");
                resp.sendRedirect(req.getContextPath() + TO_NO_ACCESS_PAGE);
            }
        }
    }
}







