package com.varukha.webproject.command.impl.manager;


import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.model.service.impl.InvoiceServiceImpl;
import com.varukha.webproject.util.DeliveryReportByDaysBuilder;
import com.varukha.webproject.util.PaymentBillBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.varukha.webproject.command.ParameterAndAttribute.LOCALE;


/**
 *  Creating delivery report by days for manager in pdf format command
 *
 * @author Dmytro Varukha
 *
 */

public class PdfCreationMangerDeliveryReportByDaysCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Executing PdfCreationMangerDeliveryReportByDays");
        Router router = new Router();
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        String deliveryDate = request.getParameter(ParameterAndAttribute.DELIVERY_DATE);

        try {
            Optional<Invoice> optionalInvoice = invoiceService.getInvoiceByDeliveryDate(deliveryDate);
            if (optionalInvoice.isPresent()) {
                DeliveryReportByDaysBuilder deliveryReportByDaysBuilder = new DeliveryReportByDaysBuilder();
                deliveryReportByDaysBuilder.getDeliveryReportByDaysInPdf(response, deliveryDate, locale);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.TO_ORDERS_MANAGER_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Service exception in getInvoiceByDeliveryDate" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }
        return router;

    }
}


