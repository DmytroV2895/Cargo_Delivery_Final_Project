package com.varukha.webproject.command.impl.manager;


import com.varukha.webproject.command.*;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.PaymentBillBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

import static com.varukha.webproject.command.ParameterAndAttribute.LOCALE;


/**
 *  Creating payment bill in pdf format command
 * @author Dmytro Varukha
 *
 */


public class PdfCreationPaymentBillCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute PdfCreationPaymentBillCommand");
        Router router = new Router();
        String locale = request.getParameter(LOCALE);
        long invoiceId = Long.parseLong(request.getParameter(ParameterAndAttribute.ID_INVOICE));
        String payerName = request.getParameter(ParameterAndAttribute.USER_NAME);
        String payerSurname = request.getParameter(ParameterAndAttribute.USER_SURNAME);
        String payerPhone = request.getParameter(ParameterAndAttribute.USER_PHONE);
        String payerEmail = request.getParameter(ParameterAndAttribute.USER_EMAIL);
        PaymentBillBuilder billToPdf = new PaymentBillBuilder();
        billToPdf.paymentBillToPdf(response, payerName, payerSurname, payerPhone, payerEmail, invoiceId, locale);
        return router;
    }
}
