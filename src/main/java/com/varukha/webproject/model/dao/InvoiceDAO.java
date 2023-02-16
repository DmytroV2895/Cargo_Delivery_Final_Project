package com.varukha.webproject.model.dao;


import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface InvoiceDAO
 * @author Dmytro Varukha
 *
 */

public interface InvoiceDAO {


    /**
     * @param resultSet;
     * @return new Invoice
     * @throws SQLException
     */
    Invoice createInvoice(ResultSet resultSet) throws SQLException;


    /**
     * @param invoice;
     * @return boolean result of operation
     * @throws SQLException
     */
    boolean addInvoice(Invoice invoice) throws DAOException;


    /**
     * Update invoice data in database
     * @param data to update
     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    boolean updateInvoiceData(Invoice data) throws DAOException, SQLException;


//    /**
//     * Get invoice by recipient phone number
//     * @param recipientPhone;
//     * @return Optional<Invoice>
//     * @throws DAOException is wrapper for SQLException
//     */
//    Optional<Invoice> findInvoiceByRecipientPhone(String recipientPhone) throws DAOException;



    /**
     * Find all sorted order info from invoice by first address
     * @param userId;
     * @return List<Invoice>
     * @throws DAOException
     */
    List<Invoice> findAllOrdersInfoFromInvoiceByDeliveryAddress(long userId, String deliveryCity, int numberOfDataOnPage, int fromRow) throws DAOException;





//    /**
//     * Find invoice by user phone
//     * @param userPhone
//     * @return Optional<Invoice>
//     * @throws DaoException
//     */
//    Optional<Invoice> findInvoiceByUserPhone(String userPhone) throws DaoException;


        /**
     * Change delivery date and order delivery status
     * @param invoice
     * @return changed invoice data
     * @throws DAOException
     */
    boolean changeInvoiceDeliveryDateAndOrderStatus(Invoice invoice) throws DAOException;


    /**
     * @param orderId;
     * @return boolean result of updating user account operation
     * @throws DAOException
     */
    boolean updateDeliveryPaymentStatusByInvoiceId(long orderId) throws DAOException;



    /**
     * @param invoiceId
     * @return List<Invoice> selected by invoiceId
     * @throws DAOException
     */
    Optional<Invoice> findInvoiceById(long invoiceId) throws DAOException;


    /**
     * @param deliveryDate
     * @return List<Invoice> selected by delivery date
     * @throws DAOException
     */
    Optional<Invoice> findInvoicesByDeliveryDate(String deliveryDate) throws DAOException;


    /**
     * @param firstCity
     * @param secondCity
     * @return List<Invoice> selected by first city (sent from) and second city (receiver)
     * @throws DAOException
     */
    Optional<Invoice> findInvoiceByDestinationCity(String firstCity, String secondCity) throws DAOException;


    /**
     * Find the number of rows of invoice table
     * @return int number of rows in database with invoices
     * @throws DAOException
     */
    int findNumberOfRowsInInvoiceTable() throws DAOException;


    /**
     * Find the number of rows of invoice table by userId
     * @param userId
     * @return int number of rows in database with invoices
     * @throws DAOException
     */
    int findNumberOfRowsInInvoiceTableByUserId(long userId) throws DAOException;


    /**
     * Find list if invoices from row of invoice table
     * @param fromRow
     * @param numberOfDataOnPage
     * @return List of invoices from row
     * @throws DAOException
     */
    List<Invoice>findAllInvoicesFromRow(int fromRow, int numberOfDataOnPage) throws DAOException;



    /**
     * Find all order info from invoice by userId
     * @param userId;
     * @param fromRow;
     * @param numberOfDataOnPage;
     * @return List of invoices from row
     * @throws DAOException
     */
    List<Invoice> findAllOrdersInfoFromInvoiceByUserId(long userId, int numberOfDataOnPage, int fromRow ) throws DAOException;


    int findNumberOfRowsInInvoiceTableByDeliveryAddress(long userId, String deliveryCity) throws DAOException;

}

