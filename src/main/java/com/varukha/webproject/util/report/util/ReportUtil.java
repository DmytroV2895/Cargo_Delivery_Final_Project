package com.varukha.webproject.util.report.util;

import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class ReportUtil contain utility methods that used to generate
 * different reports.
 */
public class ReportUtil {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Method openInBrowser used to  write the contents of the ByteArrayOutputStream
     * object to the response object and send it to the user's web browser to display a PDF file.
     * The method first sets some response headers to control the caching behavior of the browser.
     * The Expires header is set to 0, which means the response should not be cached.
     * The Cache-Control header is set to "must-revalidate, post-check=0, pre-check=0", which
     * means the browser should revalidate the response before using a cached version.
     * The Pragma header is set to "public", which indicates that the response can be cached by intermediate proxies.
     * Next, the method sets the content type of the response to "application/pdf", indicating
     * that the response contains a PDF file.
     * The method then sets the content length of the response to the size of the ByteArrayOutputStream
     * object using the setContentLength method.
     * Finally, the method writes the contents of the ByteArrayOutputStream object to the ServletOutputStream
     * of the response using the writeTo method of the ByteArrayOutputStream.
     * The flush method is called to ensure that all data is written to the output stream, and the output
     * stream is closed using the close method.
     * If an IOException occurs while writing to the output stream, the method prints the stack trace of the
     * exception using the printStackTrace method.
     *
     * @param response {@link HttpServletResponse} response from application(server side) to user (view layer).
     * @param byteArrayOutputStream output stream in which the data is written into a byte array.
     */
    public static void openInBrowser(HttpServletResponse response, ByteArrayOutputStream byteArrayOutputStream) {
        Router router = new Router();
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the content length
        response.setContentLength(byteArrayOutputStream.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byteArrayOutputStream.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.log(Level.ERROR, "IOException in openInBrowser method: " + e);
            router.setPagePath(PagePath.ERROR);
        }
    }

    /**
     * Method getBundle used to obtain ResourceBundle based on locale.
     *
     * @param locale to set ResourceBundle
     * @return ResourceBundle
     */
    public static ResourceBundle getBundle(String locale) {
        String resources = "pagecontent";
        if (locale.contains("_")) {
            String[] splitLocale = locale.split("_");
            return ResourceBundle.getBundle(resources, new Locale(splitLocale[0], splitLocale[1]));
        } else {
            return ResourceBundle.getBundle(resources, new Locale(locale));
        }
    }
}
