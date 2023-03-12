package com.varukha.webproject.controller.filters;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.User;
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
 * Class AccessFilter used to control access to application pages depend on roles.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
@WebFilter(filterName = "AccessFilter", dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD}, urlPatterns = "*.jsp")
public class AccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.log(Level.INFO, "====> Start Authentication filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAndAttribute.USER);
        String servletPath = request.getServletPath();

        boolean allowedPathGuest = ALLOWED_GUEST_COMMAND.contains(servletPath);
        boolean allowedPathUser = ALLOWED_USER_COMMAND.contains(servletPath);
        boolean allowedPathManager = ALLOWED_MANAGER_COMMAND.contains(servletPath);

        if (user != null) {
            switch (user.getRole()) {
                case USER -> {
                    if (allowedPathUser || allowedPathGuest) {
                        logger.log(Level.INFO, "====> Authentication filter. USER has permission");
                        chain.doFilter(request, response);
                    } else {
                        logger.log(Level.INFO, "====> Authentication filter. USER has no permission");
                        goToNoAccessPage(request, response);
                    }
                }
                case MANAGER -> {
                    if (allowedPathManager) {
                        logger.log(Level.INFO, "====> Authentication filter. MANAGER has permission");
                        chain.doFilter(request, response);
                    } else {
                        logger.log(Level.INFO, "====> Authentication filter. MANAGER has no permission");
                        goToNoAccessPage(request, response);
                    }
                }
            }
        } else {
            if (allowedPathGuest) {
                logger.log(Level.INFO, "====> Authentication filter. GUEST has permission");
                chain.doFilter(request, response);
            } else {
                logger.log(Level.INFO, "====> Authentication filter. GUEST has no permission");
                goToNoAccessPage(request, response);
            }
        }
    }

    private static void goToNoAccessPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + TO_NO_ACCESS_PAGE);
    }

    private static final Set<String> ALLOWED_GUEST_COMMAND = new HashSet<>(
            Arrays.asList(
                    INDEX,
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    DELIVERY_PRICE_CALCULATION_PAGE,
                    ERROR,
                    TARIFFS_PAGE,
                    DELIVERY_SERVICE_PAGE,
                    ORDER_INFO_PAGE_GUEST,
                    NO_ACCESS_PAGE,
                    HEADER

            ));

    private static final Set<String> ALLOWED_USER_COMMAND = new HashSet<>(
            Arrays.asList(
                    INDEX,
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    DELIVERY_PRICE_CALCULATION_PAGE,
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

    private static final Set<String> ALLOWED_MANAGER_COMMAND = new HashSet<>(
            Arrays.asList(
                    INDEX,
                    LOG_IN_PAGE,
                    REGISTRATION_PAGE,
                    MAIN_PAGE,
                    DELIVERY_PRICE_CALCULATION_PAGE,
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

}



