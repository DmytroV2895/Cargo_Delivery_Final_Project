package com.varukha.webproject.util.report.impl;

import com.varukha.webproject.util.report.PDFReportStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class PDFReportFactory used to create an instance of the necessary
 * report creator by type of report that was received from {@link PDFReportByReportType}.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class PDFReportFactory {
    public static final Logger logger = LogManager.getLogger();

    /**
     * Method getGeneratorForReportType used to create an instance of the report creator by type of report.
     *
     * @param reportType type of report.
     * @return an instance of report generator by necessary type of report.
     */
     public static PDFReportStrategy getGeneratorForReportType(String reportType) {
        return switch (reportType) {
             case PAYMENT_BILL -> new PaymentBillBuilder();
             case REPORT_BY_DESTINATION -> new DeliveryReportByDestinationBuilder();
             case REPORT_BY_DAYS -> new DeliveryReportByDaysBuilder();
            default ->
                    throw new IllegalArgumentException(reportType + " - unknown type of report.");
         };
     }
}

