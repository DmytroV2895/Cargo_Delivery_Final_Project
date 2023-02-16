package com.varukha.webproject.command.impl.manager;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.DeliveryReportByDestinationBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.varukha.webproject.command.ParameterAndAttribute.LOCALE;


/**
 * Creating delivery report by destination for manager in pdf format command
 *
 * @author Dmytro Varukha
 */


public class PdfCreationManagerDeliveryReportByDestinationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Executing PdfCreationManagerDeliveryReportByDestinationCommand");
        Router router = new Router();
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        String firstCity = request.getParameter(ParameterAndAttribute.FIRST_CITY);
        String secondCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);

        try {
            if (invoiceService.getInvoiceByDestinationCity(firstCity, secondCity).isPresent() ||
                    invoiceService.getInvoiceByDestinationCity(secondCity, firstCity).isPresent()) {

                DeliveryReportByDestinationBuilder deliveryReportByDestinationBuilder = new DeliveryReportByDestinationBuilder();
                deliveryReportByDestinationBuilder.getDeliveryReportByDestinationInPdf(response, firstCity, secondCity, locale);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.TO_ORDERS_MANAGER_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception in getInvoiceByDestination" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }
        return router;
    }
}

