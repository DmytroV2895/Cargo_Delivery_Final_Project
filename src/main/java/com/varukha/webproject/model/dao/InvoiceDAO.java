package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface InvoiceDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface InvoiceDAO {

    /**
     * Method createInvoice used to create set of invoice data with all information about user order.
     *
     * @param resultSet set of data about user order from database.
     * @return invoice information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    Invoice createInvoice(ResultSet resultSet) throws SQLException;

    /**
     * Method addInvoice used to adding new invoice to database.
     *
     * @param invoice contain a set of data that will be added to database.
     * @return boolean result of operation. Return true if new invoice was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean addInvoice(Invoice invoice) throws DAOException;

    /**
     * Method updateInvoiceData used to updating invoice data.
     *
     * @param data contain a set of data to change invoice.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateInvoiceData(Invoice data) throws DAOException, SQLException;

    /**
     * Method findInvoiceById used to find invoice by invoice id.
     *
     * @param invoiceId invoice id in which the search is performed.
     * @return optional result of operation. Return optional of invoice if available and return empty if not.
     * @throws DAOException is wrapper for SQLException.
     */
    Optional<Invoice> findInvoiceById(long invoiceId) throws DAOException;

    /**
     * Method findInvoiceByDeliveryDate used to find all invoices by delivery date.
     *
     * @param deliveryDate date in which the search is performed.
     * @return optional result of operation. Return optional of invoices if available and return empty if not.
     * @throws DAOException is wrapper for SQLException.
     */
    Optional<Invoice> findInvoicesByDeliveryDate(String deliveryDate) throws DAOException;

    /**
     * Method findInvoiceByDestinationCity used to find all invoice by sender city and recipient city.
     *
     * @param firstCity  sender citi city in which the search is performed.
     * @param secondCity receiver city in which the search is performed.
     * @return list of invoices by searching first city and second city.
     * @throws DAOException is wrapper for SQLException.
     */
    List<Invoice> findInvoiceByDestinationCity(String firstCity, String secondCity) throws DAOException;

    /**
     * Method updateDeliveryPaymentStatusByInvoiceId used to update delivery payment status by invoice id.
     *
     * @param invoiceId invoice id in which update is performed.
     * @return boolean result of operation of updating delivery payment status.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateDeliveryPaymentStatusByInvoiceId(long invoiceId) throws DAOException;

    /**
     * Method updateInvoiceDeliveryDateAndOrderStatus used to change delivery date and order status in invoice table.
     *
     * @param invoice contain a set of information about invoice that used to updating delivery date and order status.
     * @return boolean result of operation of updating delivery date and order status.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateInvoiceDeliveryDateAndOrderStatus(Invoice invoice) throws DAOException;

    /**
     * Method findNumberOfRecordsInInvoiceTable used to find number of records in invoice table.
     *
     * @return number of records in invoice table.
     * @throws DAOException is wrapper for SQLException.
     */
    int findNumberOfRecordsInInvoiceTable() throws DAOException;

    /**
     * Method findNumberOfRecordsInInvoiceTableByUserId used to find number of records in invoice table by user id.
     *
     * @param userId user id in which the search is performed.
     * @return number of records in invoice table by user id.
     * @throws DAOException is wrapper for SQLException.
     */
    int findNumberOfRecordsInInvoiceTableByUserId(long userId) throws DAOException;

    /**
     * Method findAllInvoicesByDeliveryDate used to find all invoices with orders' information order by delivery date.
     *
     * @param numberOfDataOnPage the number of displayed records.
     * @param dataFromRow        the number rows in invoice table.
     * @return list of all invoice from database.
     * @throws DAOException is wrapper for SQLException.
     */
    List<Invoice> findAllInvoicesByDeliveryDate(int dataFromRow, int numberOfDataOnPage) throws DAOException;

    /**
     * Method findAllOrdersInfoFromInvoiceByUserIdFromRow used to find all orders information from invoice by user id.
     *
     * @param userId             user id in which the search is performed.
     * @param numberOfDataOnPage the number of displayed records.
     * @param fromRow            the number rows in invoice table.
     * @return list of all orders information from invoice by user id from database.
     * @throws DAOException is wrapper for SQLException.
     */
    List<Invoice> findAllOrdersInfoFromInvoiceByUserId(long userId, int numberOfDataOnPage, int fromRow) throws DAOException;

    /**
     * Method findAllOrdersInfoFromInvoiceByDeliveryCity used to find all orders information from invoice by delivery address.
     *
     * @param userId             user id in which the search is performed.
     * @param deliveryCity       the city in which the search is performed.
     * @param numberOfDataOnPage the number of displayed records.
     * @param fromRow            the number rows in invoice table.
     * @return list of all orders information from invoice by delivery city from database.
     * @throws DAOException is wrapper for SQLException.
     */
    List<Invoice> findAllOrdersInfoFromInvoiceByDeliveryCity(long userId, String deliveryCity, int numberOfDataOnPage, int fromRow) throws DAOException;

    /**
     * Method findNumberOfRecordsInInvoiceTableByDeliveryCity used to find number of records in invoice table by delivery city.
     *
     * @param userId       user id in which the search is performed.
     * @param deliveryCity delivery city in which the search is performed.
     * @return number of rows in invoice table by delivery city.
     * @throws DAOException is wrapper for SQLException.
     */
    int findNumberOfRecordsInInvoiceTableByDeliveryCity(long userId, String deliveryCity) throws DAOException;

}

