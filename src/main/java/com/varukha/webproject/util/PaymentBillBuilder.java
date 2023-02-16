package com.varukha.webproject.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.model.service.impl.InvoiceServiceImpl;
import jakarta.servlet.http.HttpServletResponse;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;


/**
 * PDF builder util. Generates pdf-report for manager.
 *
 * @author Dmytro Varukha.
 */
public class PaymentBillBuilder {


    /**
     * Generates pdf-files for payment bill.
     */

    public void paymentBillToPdf(HttpServletResponse response, String payerName,
                                        String payerSurname, String payerPhone,
                                        String payerEmail, long invoiceId, String locale) {

        InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl(ConnectionPool.getInstance()));


        Document document = new Document(PageSize.A4, 40, 20, 20, 20);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ResourceBundle resourceBundle = getBundle(locale);

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            BaseFont arial = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", "cp1251", BaseFont.EMBEDDED);


            Paragraph title = new Paragraph(resourceBundle.getString("user_payment_bill_page"), new Font(arial, 20));

            Chapter chapter = new Chapter(title, 1);
            chapter.setNumberDepth(0);

            Paragraph title1 = new Paragraph(resourceBundle.getString("user_info_bill_page"), new Font(arial, 14));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);


            Section section1 = chapter.addSection(title1);

            Paragraph payerInformation = new Paragraph(resourceBundle.getString("user_name_bill_page") + " " + payerName, new Font(arial, 12));
            section1.add(payerInformation);


            String userSurname = resourceBundle.getString("user_surname_bill_page") + " " + payerSurname;
            payerInformation = new Paragraph(userSurname, new Font(arial, 12));
            section1.add(payerInformation);


            String userEmail = resourceBundle.getString("user_email_bill_page") + " " +  payerEmail;
            payerInformation = new Paragraph(userEmail, new Font(arial, 12));
            section1.add(payerInformation);


            String userPhone = resourceBundle.getString("user_phone_bill_page") + " " +  payerPhone;
            payerInformation = new Paragraph(userPhone, new Font(arial, 12));
            section1.add(payerInformation);


            PdfPTable table = new PdfPTable(6);
            table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            table.setSpacingBefore(30);
            table.setSpacingAfter(25);

            PdfPCell c1 = new PdfPCell(new Phrase(resourceBundle.getString("order_name_bill_page"), new Font(arial, 12)));
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase(resourceBundle.getString("order_description_bill_page"), new Font(arial, 12)));
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase(resourceBundle.getString("order_number_bill_page"), new Font(arial, 12)));
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c3);

            PdfPCell c4 = new PdfPCell(new Phrase(resourceBundle.getString("order_price_bill_page"), new Font(arial, 12)));
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c4);

            PdfPCell c5 = new PdfPCell(new Phrase(resourceBundle.getString("delivery_price_bill_page"), new Font(arial, 12)));
            c5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c5);

            PdfPCell c6 = new PdfPCell(new Phrase(resourceBundle.getString("order_total_price_bill_page"), new Font(arial, 12)));
            c6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c6);


            Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(invoiceId);
            if (invoiceOptional.isPresent()) {
                List<Invoice> invoiceList = Converter.toList(invoiceOptional);

                for (Invoice invoice1 : invoiceList) {
                    table.addCell(new Phrase(String.valueOf(invoice1.getOrder().getOrderName()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getOrder().getOrderDescription()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getOrder().getOrderId()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getOrder().getPrice()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getDeliveryPrice()), new Font(arial, 12)));
                    table.addCell(new Phrase(String.valueOf(invoice1.getTotalPrice()), new Font(arial, 12)));
                }
                section1.add(table);
                document.add(chapter);
                document.close();
                openInBrowser(response, byteArrayOutputStream);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }


    private static void openInBrowser(HttpServletResponse response, ByteArrayOutputStream byteArrayOutputStream) {
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the content length
        response.setContentLength(byteArrayOutputStream.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byteArrayOutputStream.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains ResourceBundle based on locale.
     * @param locale to set ResourceBundle
     * @return ResourceBundle
     */
    private ResourceBundle getBundle(String locale) {
        String resources = "pagecontent";
        if (locale.contains("_")) {
            String[] splitLocale = locale.split("_");
            return ResourceBundle.getBundle(resources, new Locale(splitLocale[0], splitLocale[1]));
        } else {
            return ResourceBundle.getBundle(resources, new Locale(locale));
        }
    }

}












