package com.varukha.webproject.command.impl.manager;

import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.report.impl.PDFReportByReportType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class PDFReportCommand it is a command type that contain a methods
 * that used to create reports in pdf format:
 * - payment bill,
 * - report by definite days of cargo delivery,
 * - report by destination deliver city (from/to).
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class PDFReportCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    PDFReportByReportType pdfReportByReportType;
    private static final InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

    public PDFReportCommand(PDFReportByReportType pdfReportByReportType) {
        this.pdfReportByReportType = pdfReportByReportType;
    }

    /**
     * Method execute use as start point of executing PDFReportCommand.
     *
     * @param request  {@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return route to the specified page.
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        logger.log(Level.INFO, "Execute PDFReportCommand");
        Router router = new Router();
        HttpSession session = request.getSession();

        String reportType = request.getParameter(ParameterAndAttribute.TYPE_OF_REPORT);
        String locale = request.getParameter(LOCALE);
        long invoiceId = Long.parseLong(request.getParameter(ParameterAndAttribute.ID_INVOICE));
        String payerName = request.getParameter(ParameterAndAttribute.USER_NAME);
        String payerSurname = request.getParameter(ParameterAndAttribute.USER_SURNAME);
        String payerPhone = request.getParameter(ParameterAndAttribute.USER_PHONE);
        String payerEmail = request.getParameter(ParameterAndAttribute.USER_EMAIL);
        String deliveryDate = request.getParameter(ParameterAndAttribute.DELIVERY_DATE);
        String firstCity = request.getParameter(ParameterAndAttribute.FIRST_CITY);
        String secondCity = request.getParameter(ParameterAndAttribute.SECOND_CITY);

        switch (reportType) {
            case PAYMENT_BILL -> {
                logger.log(Level.INFO, "Create PaymentBill");
                pdfReportByReportType.generatePDFReport(response, reportType, deliveryDate, locale, firstCity, secondCity,
                        payerName, payerSurname, payerPhone, payerEmail, invoiceId);
            }
            case REPORT_BY_DAYS -> {
                logger.log(Level.INFO, "Create ReportByDays");
                generatePdfReportByDays(request, response, router, session, reportType, locale,
                        invoiceId, payerName, payerSurname, payerPhone, payerEmail, deliveryDate,
                        firstCity, secondCity);
            }
            case REPORT_BY_DESTINATION -> {
                logger.log(Level.INFO, "Create ReportByDestination");
                generatePdfReportByDestinationCityCommand(request, response, router, session, reportType,
                        locale, invoiceId, payerName, payerSurname, payerPhone, payerEmail, deliveryDate,
                        firstCity, secondCity);
            }
        }
        return router;
    }

    /**
     * Method generatePdfReportByDays it is a method type
     * that used to create report in pdf format by definite days of cargo delivery.
     *
     * @param request      {@link HttpServletRequest} request from client side.
     * @param response     {@link HttpServletResponse} response to client side.
     * @param router       {@link Router} used to routing to another or current page.
     * @param session      {@link HttpSession} created session by user.
     * @param reportType   type of report to create report in pdf format.
     * @param locale       locale from jsp page to display generated pdf report in the current language.
     * @param invoiceId    invoice id to create report by specified invoice.
     * @param payerName    name of person who provide payment operations.
     * @param payerSurname surname of person who provide payment operations.
     * @param payerPhone   phone number of person who provide payment operations.
     * @param payerEmail   email of person who provide payment operations.
     * @param deliveryDate delivery date of the order.
     * @param firstCity    city from the order is sent.
     * @param secondCity   city where the order is received.
     */
    private void generatePdfReportByDays(HttpServletRequest request, HttpServletResponse response,
                                         Router router, HttpSession session, String reportType,
                                         String locale, long invoiceId, String payerName, String payerSurname,
                                         String payerPhone, String payerEmail, String deliveryDate,
                                         String firstCity, String secondCity) {
        try {
            Optional<Invoice> optionalInvoice = invoiceService.getInvoiceByDeliveryDate(deliveryDate);
            if (optionalInvoice.isPresent()) {
                logger.log(Level.INFO, "Generate ReportByDays");
                pdfReportByReportType.generatePDFReport(response, reportType, deliveryDate, locale, firstCity, secondCity, payerName,
                        payerSurname, payerPhone, payerEmail, invoiceId);

            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.TO_ORDERS_MANAGER_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getInvoiceByDeliveryDate" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }

    }

    /**
     * Method generatePdfReportByDestinationCityCommand it is a command type
     * that used to create report in pdf format by destination city (from/to) of cargo delivery.
     *
     * @param request      {@link HttpServletRequest} request from client side.
     * @param response     {@link HttpServletResponse} response to client side.
     * @param router       {@link Router} used to routing to another or current page.
     * @param session      {@link HttpSession} created session by user.
     * @param reportType   type of report to create report in pdf format.
     * @param locale       locale from jsp page to display generated pdf report in the current language.
     * @param invoiceId    invoice id to create report by specified invoice.
     * @param payerName    name of person who provide payment operations.
     * @param payerSurname surname of person who provide payment operations.
     * @param payerPhone   phone number of person who provide payment operations.
     * @param payerEmail   email of person who provide payment operations.
     * @param deliveryDate delivery date of the order.
     * @param firstCity    city from the order is sent.
     * @param secondCity   city where the order is received.
     */
    private void generatePdfReportByDestinationCityCommand(HttpServletRequest request, HttpServletResponse response,
                                                           Router router, HttpSession session, String reportType,
                                                           String locale, long invoiceId, String payerName, String payerSurname,
                                                           String payerPhone, String payerEmail, String deliveryDate,
                                                           String firstCity, String secondCity) {
        try {
            List<Invoice> invoiceList = invoiceService.getInvoiceByDestinationCity(firstCity, secondCity);
            if (invoiceList.size() != 0) {
                logger.log(Level.INFO, "Generate ReportByDestination");
                pdfReportByReportType.generatePDFReport(response, reportType, deliveryDate, locale, firstCity, secondCity, payerName,
                        payerSurname, payerPhone, payerEmail, invoiceId);
            } else {
                logger.log(Level.ERROR, "Nothing were found");
                request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
                session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.ORDERS_MANAGER_PAGE);
                router.setPagePath(PagePath.TO_ORDERS_MANAGER_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "ServiceException in getInvoiceByDestinationCity" + e.getMessage());
            router.setPagePath(PagePath.ERROR);
        }
    }
}







