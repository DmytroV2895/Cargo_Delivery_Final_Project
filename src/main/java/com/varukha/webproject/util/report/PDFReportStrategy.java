package com.varukha.webproject.util.report;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface PDFReportStrategy contain contracts of methods for
 * creating different reports in pdf format by specified type of report.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface PDFReportStrategy {

/**
 * Method generateReport contain contracts to implement for
 * creating reports in pdf format by specified type of report.
 */
  void generateReport(HttpServletResponse response,
                                         String deliveryDate,
                                         String locale,
                                         String firstCity,
                                         String secondCity,
                                         String payerName,
                                         String payerSurname,
                                         String payerPhone,
                                         String payerEmail,
                                         long invoiceId);

}
