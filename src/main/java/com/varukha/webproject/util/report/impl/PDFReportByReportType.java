package com.varukha.webproject.util.report.impl;

import com.varukha.webproject.util.report.PDFReportStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class PDFReportByReportType used to select the necessary
 * report generator by type of report from {@link PDFReportFactory} to create pdf report.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class PDFReportByReportType {

    public static final Logger logger = LogManager.getLogger();

    PDFReportFactory pdfReportFactory;
    public PDFReportByReportType(PDFReportFactory pdfReportFactory) {
     this.pdfReportFactory = pdfReportFactory;
    }

    /**
     * Method generatePDFReport it is a method that used to define type of report
     * and generate report in pdf format type of report from request.
     *
     * @param response     {@link HttpServletResponse} response to client side.
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
        public void generatePDFReport(HttpServletResponse response,
                                             String reportType,
                                             String deliveryDate,
                                             String locale,
                                             String firstCity,
                                             String secondCity,
                                             String payerName,
                                             String payerSurname,
                                             String payerPhone,
                                             String payerEmail,
                                             long invoiceId) {
            PDFReportStrategy pdfReportStrategy = PDFReportFactory.getGeneratorForReportType(reportType);
            pdfReportStrategy.generateReport( response, deliveryDate, locale, firstCity, secondCity,
                     payerName, payerSurname, payerPhone, payerEmail, invoiceId);
        }
    }


