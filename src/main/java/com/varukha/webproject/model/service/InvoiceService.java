package com.varukha.webproject.model.service;

import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * The interface InvoiceService
 * @author Dmytro Varukha
 * @version 1.0
 */


public interface InvoiceService {

    /**
     * Save new invoice to database
     * @param invoiceData;
     * @return boolean result of operation
     * @throws ServiceException
     */
    boolean addInvoice(Map<String, String> invoiceData) throws ServiceException, IncorrectInputException;

    /**
     * Update invoice data in database
     * @param data to update current invoice information
     * @return boolean result of operation
     * @throws ServiceException
     * @throws IncorrectInputException
     */
    boolean updateInvoice(Map<String, String> data) throws ServiceException, IncorrectInputException;


//    /**
//     * Get invoice by recipient phone number
//     * @param recipientPhone;
//     * @return Optional<Invoice>
//     * @throws ServiceException
//     */
//    Optional<Invoice> getInvoiceByRecipientPhone(String recipientPhone) throws ServiceException;



    /**
     * @param orderId;

     * @return boolean result of updating user account operation
     * @throws ServiceException
     */
    boolean updateDeliveryPaymentStatusByInvoiceId(long orderId) throws ServiceException;


    /**
     * @param invoiceData;

     * @return boolean result of updating delivery date and order delivery status
     * @throws ServiceException
     */
    boolean updateInvoiceDeliveryDateAndOrderStatus(Map<String, String> invoiceData) throws ServiceException;



    /**
     * @param invoiceId
     * @return List<Invoice>
     * @throws ServiceException
     */
    Optional<Invoice> getInvoiceById(long invoiceId) throws ServiceException;


    /**
     * @param deliveryDate
     * @return Optional<Invoice>
     * @throws ServiceException
     */
    Optional<Invoice> getInvoiceByDeliveryDate(String deliveryDate) throws ServiceException;


    /**
     * @param firstCity
     * @param secondCity
     * @return List<Invoice> selected by first city (sent from) and second city (receiver)
     * @throws ServiceException
     */
    Optional<Invoice> getInvoiceByDestinationCity(String firstCity, String secondCity) throws ServiceException;


    /**
     * Get the number of rows of invoice table
     * @return int number of rows from database invoice table
     * @throws ServiceException
     */
    int getNumberOfPages() throws ServiceException;

    /**
     * Get the number of rows of invoice table by userId
     * @param userId
     * @return int number of rows from database invoice table
     * @throws ServiceException
     */
    int getNumberOfRowsInInvoiceTableByUserId(long userId) throws ServiceException;


    /**
     * Get list if invoices from row of invoice table according to page number
     * @param pageNumber
     * @return List of invoices from row
     * @throws ServiceException
     */
    List<Invoice> getInvoicesFromRow(int pageNumber) throws ServiceException;



    /**
     * Find all order info from invoice by userId
     * @param userId;
     * @param pageNumber;
     * @return List of invoices from row by userId
     * @throws ServiceException
     */
    List<Invoice> getAllOrdersInfoFromInvoiceByUserId(long userId, int pageNumber) throws ServiceException;


    /**
     * Find all sorted order info from invoice by first address
     * @param userId;
     * @param pageNumber;
     * @return Sorted list of invoices from row by first address
     * @throws ServiceException
     */
    List<Invoice> getAllSortedOrdersInfoFromInvoiceByDeliveryAddress(long userId, String deliveryCity, int pageNumber) throws ServiceException;


    int getNumberOfRowsInInvoiceTableByDeliveryAddress(long deliveryAddressId, String deliveryCity) throws ServiceException;
}
