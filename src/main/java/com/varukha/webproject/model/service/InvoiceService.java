package com.varukha.webproject.model.service;

import com.varukha.webproject.model.entity.Invoice;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Interface InvoiceService contain contracts of service methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface InvoiceService {

    /**
     * Method addInvoice is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new invoice using data access layer.
     *
     * @param invoiceData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean addInvoice(Map<String, String> invoiceData) throws ServiceException, IncorrectInputException;

    /**
     * Method updateInvoice is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update invoice data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException        is wrapper for DAOException that throws exception during the runtime because of
     *                                 data validation fail or others mistakes.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    boolean updateInvoice(Map<String, String> data) throws ServiceException, IncorrectInputException;

    /**
     * Method getInvoiceById is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get invoice data by invoice id.
     *
     * @param invoiceId invoice id in which the search is performed.
     * @return optional result of operation. Return optional of invoice if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    Optional<Invoice> getInvoiceById(long invoiceId) throws ServiceException;

    /**
     * Method getInvoiceByDeliveryDate is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get all invoices by delivery date.
     *
     * @param deliveryDate date in which the search is performed.
     * @return optional result of operation. Return optional of invoices if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    Optional<Invoice> getInvoiceByDeliveryDate(String deliveryDate) throws ServiceException;

    /**
     * Method getInvoiceByDestinationCity is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get all invoice by sender city and recipient city.
     *
     * @param firstCity  sender city in which the search is performed.
     * @param secondCity receiver city in which the search is performed.
     * @return list of invoices by searching first city and second city.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    List<Invoice> getInvoiceByDestinationCity(String firstCity, String secondCity) throws ServiceException;

    /**
     * Method updateDeliveryPaymentStatusByInvoiceId is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update delivery payment status by invoice id.
     *
     * @param invoiceId invoice id in which update is performed.
     * @return boolean result of operation of updating delivery payment status.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean updateDeliveryPaymentStatusByInvoiceId(long invoiceId) throws ServiceException;

    /**
     * Method updateInvoiceDeliveryDateAndOrderStatus is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update delivery date and order status.
     *
     * @param invoiceData contain an information about delivery date and order status that used to updating.
     * @return boolean result of operation of updating delivery date and order status.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean updateInvoiceDeliveryDateAndOrderStatus(Map<String, String> invoiceData) throws ServiceException;

    /**
     * Method getNumberOfRecordsInInvoiceTableByUserId is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get number of records in invoice table by user id.
     *
     * @param userId user id in which the search is performed.
     * @return number of records in invoice table by user id.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    int getNumberOfRecordsInInvoiceTableByUserId(long userId) throws ServiceException;

    /**
     * Method getNumberOfRecordsOfAllInvoices is an intermediate service method for communication between
     * the user view layer and the database and used to get number of all records in invoice table for manager.
     *
     * @return number of all records in invoice table.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    int getNumberOfRecordsOfAllInvoices() throws ServiceException;

    /**
     * Method getInvoicesFromPagePagination is an intermediate service method for communication between
     * the user view layer and the database and used get to definite records from invoice table
     * and display of data after switching to a new page.
     *
     * @param pageNumber page number with displayed data.
     * @return definite data from invoice table for specified page.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    List<Invoice> getInvoicesFromPagePagination(int pageNumber) throws ServiceException;

    /**
     * Method getAllOrdersInfoFromInvoiceByUserId is an intermediate service method for communication between
     * the user view layer and the database and used to get all orders information from invoice by user id.
     *
     * @param userId     user id in which the search is performed.
     * @param pageNumber page number with displayed data.
     * @return list of all orders information from invoice by user id.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    List<Invoice> getAllOrdersInfoFromInvoiceByUserId(long userId, int pageNumber) throws ServiceException;

    /**
     * Method getAllFilteredOrdersInfoFromInvoiceByDeliveryCity is an intermediate service method for communication between
     * the user view layer and the database and used to get orders information from invoice that was filtered by delivery city.
     *
     * @param userId       user id in which the search is performed.
     * @param deliveryCity delivery city in which the search is performed.
     * @param pageNumber   page number with displayed data.
     * @return list of orders information from invoice that was filtered by delivery city.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    List<Invoice> getAllFilteredOrdersInfoFromInvoiceByDeliveryCity(long userId, String deliveryCity, int pageNumber) throws ServiceException;

    /**
     * Method getNumberOfRecordsInInvoiceTableByDeliveryCityPagination is an intermediate service method for communication between
     * the user view layer and the database and used to get number of records in invoice table that was filtered by delivery city
     * and display of data after switching to a new page.
     *
     * @param userId       user id in which the search is performed.
     * @param deliveryCity delivery city in which the search is performed.
     * @return number of all records in invoice table after filtering.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    int getNumberOfRecordsInInvoiceTableByDeliveryCityPagination(long userId, String deliveryCity) throws ServiceException;
}
