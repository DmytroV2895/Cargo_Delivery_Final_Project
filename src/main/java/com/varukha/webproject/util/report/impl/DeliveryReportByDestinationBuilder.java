package com.varukha.webproject.util.report.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.Converter;
import com.varukha.webproject.util.report.PDFReportStrategy;
import com.varukha.webproject.util.report.util.ReportUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Class DeliveryReportByDestinationBuilder used to generate pdf report by destination city
 * by using Itext pdf library.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class DeliveryReportByDestinationBuilder implements PDFReportStrategy {

    /**
     * Method generateReport use to generate pdf files for delivery report by destination.
     */
    @Override
    public void generateReport(HttpServletResponse response,
                               String deliveryDate,
                               String locale,
                               String firstCity,
                               String secondCity,
                               String payerName,
                               String payerSurname, String payerPhone,
                               String payerEmail, long invoiceId) {

        InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();

        Document document = new Document(PageSize.A4.rotate(), 20, 10, 20, 20);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResourceBundle resourceBundle = ReportUtil.getBundle(locale);

        try {

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            BaseFont arial = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", "cp1251", BaseFont.EMBEDDED);

            Paragraph title = new Paragraph(resourceBundle.getString("report_title_report_by_dest"), new Font(arial, 20));

            Chapter chapter = new Chapter(title, 1);
            chapter.setNumberDepth(0);

            Paragraph title1 = new Paragraph(resourceBundle.getString("report_subtitle_report_by_dest"), new Font(arial, 14));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);

            Section section1 = chapter.addSection(title1);

            PdfPTable table = new PdfPTable(new PdfPTable(new float[]{12, 10, 16, 11, 11, 11, 12, 11, 10, 12, 14}));
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setSpacingBefore(30);
            table.setSpacingAfter(25);

            PdfPCell c1 = new PdfPCell(new Phrase(resourceBundle.getString("invoice_num_report_by_dest"), new Font(arial, 12)));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase(resourceBundle.getString("order_type_report_by_dest"), new Font(arial, 12)));
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(resourceBundle.getString("sender_city_report_by_dest"), new Font(arial, 12)));
            c3.setBackgroundColor(BaseColor.YELLOW);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c3);

            PdfPCell c4 = new PdfPCell(new Phrase(resourceBundle.getString("sender_street_name_report_by_dest"), new Font(arial, 12)));
            c4.setBackgroundColor(BaseColor.YELLOW);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c4);

            PdfPCell c5 = new PdfPCell(new Phrase(resourceBundle.getString("sender_street_num_report_by_dest"), new Font(arial, 12)));
            c5.setBackgroundColor(BaseColor.YELLOW);
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c5);

            PdfPCell c6 = new PdfPCell(new Phrase(resourceBundle.getString("sender_house_num_report_by_dest"), new Font(arial, 12)));
            c6.setBackgroundColor(BaseColor.YELLOW);
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c6);


            PdfPCell c7 = new PdfPCell(new Phrase(resourceBundle.getString("receiver_city_report_by_dest"), new Font(arial, 12)));
            c7.setBackgroundColor(BaseColor.CYAN);
            c7.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c7);

            PdfPCell c8 = new PdfPCell(new Phrase(resourceBundle.getString("receiver_street_name_report_by_dest"), new Font(arial, 12)));
            c8.setBackgroundColor(BaseColor.CYAN);
            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c8);

            PdfPCell c9 = new PdfPCell(new Phrase(resourceBundle.getString("receiver_street_num_report_by_dest"), new Font(arial, 12)));
            c9.setBackgroundColor(BaseColor.CYAN);
            c9.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c9);

            PdfPCell c10 = new PdfPCell(new Phrase(resourceBundle.getString("receiver_house_num_report_by_dest"), new Font(arial, 12)));
            c10.setBackgroundColor(BaseColor.CYAN);
            c10.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c10);


            PdfPCell c11 = new PdfPCell(new Phrase(resourceBundle.getString("delivery_date_report_by_dest"), new Font(arial, 12)));
            c11.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c11.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c11);


            List<Invoice> invoiceList = invoiceService.getInvoiceByDestinationCity(firstCity, secondCity);
            if (invoiceList != null) {
                for (Invoice invoice1 : invoiceList) {
                    table.addCell(new Phrase(String.valueOf(invoice1.getInvoiceId()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getOrder().getType()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressFirst().getFirstCity()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressFirst().getFirstStreetName()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressFirst().getFirstStreetNumber()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressFirst().getFirstHouseNumber()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressSecond().getSecondCity()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressSecond().getSecondStreetName()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressSecond().getSecondStreetNumber()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getAddressSecond().getSecondHouseNumber()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getDeliveryDate()), new Font(arial, 12)));
                }
                section1.add(table);
                document.add(chapter);
                document.close();
                ReportUtil.openInBrowser(response, byteArrayOutputStream);
            } else {

            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}


