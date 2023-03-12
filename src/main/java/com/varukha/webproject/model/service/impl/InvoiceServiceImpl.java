package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.model.entity.*;
import com.varukha.webproject.model.entity.Invoice.OrderStatus;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.InvoiceDAO;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.util.validation.DataValidator;
import com.varukha.webproject.util.calculator.impl.PriceCalculatorByOrderType;
import com.varukha.webproject.util.calculator.impl.TotalDeliveryPriceCalculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static com.varukha.webproject.command.ParameterAndAttribute.*;


/**
 * Class InvoiceServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LogManager.getLogger();
    private final InvoiceDAO invoiceDAO;
    private final int numberOfInvoicesOnPage = 3;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    /**
     * Method addInvoice is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new invoice to database.
     *
     * @param invoiceData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean addInvoice(Map<String, String> invoiceData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Save invoice to database. Invoice data" + invoiceData);
        boolean isAdded;
        if (isInvoiceDataValid(invoiceData)) {

            Invoice invoice = new Invoice.Builder()
                    .setUser(createUser(invoiceData.get(USER_ID)))
                    .setOrder(createOrder(invoiceData.get(ID_ORDER)))
                    .setDelivery(createDelivery(invoiceData.get(DELIVERY_ID)))
                    .setDeliveryPrice(calculateDeliveryPrice(invoiceData))
                    .setTotalPrice(calculateTotalPrice(invoiceData))
                    .setDeliveryPaymentStatus(false)
                    .setOrderStatus(OrderStatus.NOT_PROCESSED)
                    .setFirstAddress(createFirstAddress(invoiceData.get(FIRST_ADDRESS_ID)))
                    .setSecondAddress(createSecondAddress(invoiceData.get(SECOND_ADDRESS_ID)))
                    .build();
            try {
                isAdded = invoiceDAO.addInvoice(invoice);
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in addInvoice method: " + e);
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.DEBUG, "Incorrect data input" + invoiceData);
            throw new IncorrectInputException("Incorrect data input" + invoiceData);
        }
        return isAdded;
    }

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
    @Override
    public boolean updateInvoice(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Update invoice in database. Invoice data" + data);
        boolean isChanged = false;
        if (isInvoiceDataValid(data)) {
            Invoice invoice = new Invoice.Builder()
                    .setInvoiceId(Long.parseLong(data.get(ID_INVOICE)))
                    .setDeliveryPrice(calculateDeliveryPrice(data))
                    .setTotalPrice(calculateTotalPrice(data))
                    .build();
            try {
                isChanged = invoiceDAO.updateInvoiceData(invoice);
                logger.log(Level.DEBUG, "Updated invoice data" + data);
            } catch (DAOException | SQLException e) {
                logger.log(Level.ERROR, "DAO exception in updateInvoice method: " + e);
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.DEBUG, "Incorrect data input" + data);
            throw new IncorrectInputException("Incorrect data input" + data);
        }
        return isChanged;
    }

    /**
     * Method getInvoiceById is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get invoice data by invoice id.
     *
     * @param invoiceId invoice id in which the search is performed.
     * @return optional result of operation. Return optional of invoice if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public Optional<Invoice> getInvoiceById(long invoiceId) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoice by Id:" + invoiceId);
        Optional<Invoice> invoice;
        try {
            invoice = invoiceDAO.findInvoiceById(invoiceId);
            logger.log(Level.DEBUG, "Invoice was found" + invoice);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceById method.  InvoiceId: " + invoiceId);
            throw new ServiceException(e);
        }
        return invoice;
    }

    /**
     * Method getInvoiceByDeliveryDate is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get all invoices by delivery date.
     *
     * @param deliveryDate date in which the search is performed.
     * @return optional result of operation. Return optional of invoices if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public Optional<Invoice> getInvoiceByDeliveryDate(String deliveryDate) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoice by deliveryDate:" + deliveryDate);
        Optional<Invoice> optionalInvoice;
        try {
            optionalInvoice = invoiceDAO.findInvoicesByDeliveryDate(deliveryDate);
            logger.log(Level.DEBUG, "Invoice was found. " + optionalInvoice + " Delivery date " + deliveryDate);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceByDeliveryDate method.  Delivery date " + deliveryDate);
            throw new ServiceException(e);
        }
        return optionalInvoice;
    }

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
    @Override
    public List<Invoice> getInvoiceByDestinationCity(String firstCity, String secondCity) throws ServiceException {
        logger.log(Level.INFO, "Get invoice by destination. First city: " + firstCity + " Second city: " + secondCity);
        List<Invoice> invoiceList;
        try {
            invoiceList = invoiceDAO.findInvoiceByDestinationCity(firstCity, secondCity);
                logger.log(Level.DEBUG, "Invoice was found. " + invoiceList + " First city: " + firstCity + " Second city: " + secondCity);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceByDestinationCity method.  First city: " + firstCity + " Second city: " + secondCity);
            throw new ServiceException(e);
        }
        return invoiceList;
    }

    /**
     * Method updateDeliveryPaymentStatusByInvoiceId is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update delivery payment status by invoice id.
     *
     * @param invoiceId invoice id in which update is performed.
     * @return boolean result of operation of updating delivery payment status.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean updateDeliveryPaymentStatusByInvoiceId(long invoiceId) throws ServiceException {
        logger.log(Level.DEBUG, "Update delivery payment status by invoiceId: " + invoiceId);
        boolean isUpdated;
        try {
            isUpdated = invoiceDAO.updateDeliveryPaymentStatusByInvoiceId(invoiceId);
        } catch (DAOException e) {
            logger.log(Level.ERROR,
                    "DAO exception in method updateDeliveryPaymentStatusByInvoiceId, when we try update payment status by invoiceId :" + invoiceId + ". " + e);
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    /**
     * Method updateInvoiceDeliveryDateAndOrderStatus is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update delivery date and order status.
     *
     * @param invoiceData contain an information about delivery date and order status that used to updating.
     * @return boolean result of operation of updating delivery date and order status.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean updateInvoiceDeliveryDateAndOrderStatus(Map<String, String> invoiceData) throws ServiceException {
        logger.log(Level.INFO, "Update invoice delivery invoiceData and order status by manager " + invoiceData);
        boolean isUpdated = false;
        try {
            Date deliveryDate = Date.valueOf(invoiceData.get(DELIVERY_DATE));
            OrderStatus orderStatus = OrderStatus.valueOf(invoiceData.get(ORDER_STATUS));

            Invoice invoice = new Invoice.Builder()
                    .setInvoiceId(Long.parseLong(invoiceData.get(ID_INVOICE)))
                    .setDeliveryDate(deliveryDate)
                    .setOrderStatus(orderStatus)
                    .build();

            isUpdated = invoiceDAO.updateInvoiceDeliveryDateAndOrderStatus(invoice);
        } catch (DAOException e) {
            logger.log(Level.ERROR,
                    "DAO exception in updateInvoiceDeliveryDateAndOrderStatus method: " + invoiceData + ". " + e);
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    /**
     * Method getNumberOfRecordsInInvoiceTableByUserId is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to get number of records in invoice table by user id.
     *
     * @param userId user id in which the search is performed.
     * @return number of records in invoice table by user id.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public int getNumberOfRecordsInInvoiceTableByUserId(long userId) throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages by userId:");
        int numberOfPages;
        int numberOfInvoices;
        try {
            numberOfInvoices = invoiceDAO.findNumberOfRecordsInInvoiceTableByUserId(userId);
            if (numberOfInvoices > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfInvoices / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getNumberOfRecordsInInvoiceTableByUserId method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

    /**
     * Method getNumberOfRecordsOfAllInvoices is an intermediate service method for communication between
     * the user view layer and the database and used to get number of all records in invoice table for manager.
     *
     * @return number of all records in invoice table.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public int getNumberOfRecordsOfAllInvoices() throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages:");
        int numberOfPages;
        int numberOfInvoices;
        try {
            numberOfInvoices = invoiceDAO.findNumberOfRecordsInInvoiceTable();
            if (numberOfInvoices > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfInvoices / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getNumberOfRecordsOfAllInvoices method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

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
    @Override
    public List<Invoice> getInvoicesFromPagePagination(int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoices from row of invoice table. Page number: " + pageNumber);
        List<Invoice> invoices;
        int invoiceFromRow;
        if (pageNumber > 1) {
            invoiceFromRow = (pageNumber - 1) * numberOfInvoicesOnPage;
        } else {
            invoiceFromRow = 0;
        }
        try {
            invoices = invoiceDAO.findAllInvoicesByDeliveryDate(invoiceFromRow, numberOfInvoicesOnPage);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoicesFromPagePagination method " + e);
            throw new ServiceException(e);
        }
        return invoices;
    }

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
    @Override
    public List<Invoice> getAllOrdersInfoFromInvoiceByUserId(long userId, int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get all order info from invoice by userId:" + userId);
        List<Invoice> invoiceList;
        int invoiceFromRow = getInvoicePagesFromRow(pageNumber);
        try {
            invoiceList = invoiceDAO.findAllOrdersInfoFromInvoiceByUserId(userId, numberOfInvoicesOnPage, invoiceFromRow);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getAllOrdersInfoFromInvoiceByUserId method " + e);
            throw new ServiceException(e);
        }
        return invoiceList;
    }

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
    @Override
    public List<Invoice> getAllFilteredOrdersInfoFromInvoiceByDeliveryCity(long userId, String deliveryCity, int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get all sorted order info from invoice by delivery city:" + userId);
        List<Invoice> invoiceList;
        int invoiceFromRow = getInvoicePagesFromRow(pageNumber);
        try {
            invoiceList = invoiceDAO.findAllOrdersInfoFromInvoiceByDeliveryCity(userId, deliveryCity, numberOfInvoicesOnPage, invoiceFromRow);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getAllFilteredOrdersInfoFromInvoiceByDeliveryCity method " + e);
            throw new ServiceException(e);
        }
        return invoiceList;
    }

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
    @Override
    public int getNumberOfRecordsInInvoiceTableByDeliveryCityPagination(long userId, String deliveryCity) throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages by delivery city:");
        int numberOfPages;
        int numberOfOrders;
        try {
            numberOfOrders = invoiceDAO.findNumberOfRecordsInInvoiceTableByDeliveryCity(userId, deliveryCity);
            if (numberOfOrders > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfOrders / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getNumberOfRecordsInInvoiceTableByDeliveryCityPagination method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

    /**
     * Method getInvoicePagesFromRow used to calculate total number of pages.
     *
     * @param pageNumber number of pages.
     * @return total number of pages on user information page.
     */
    private int getInvoicePagesFromRow(int pageNumber) {
        int invoiceFromRow;
        if (pageNumber > 1) {
            invoiceFromRow = (pageNumber - 1) * numberOfInvoicesOnPage;
        } else {
            invoiceFromRow = 0;
        }
        return invoiceFromRow;
    }

    /**
     * Method calculateDeliveryPrice used to calculate order volume.
     *
     * @param calculationData data from order creation page.
     * @return order volume value.
     */
    static BigDecimal calculateDeliveryPrice(Map<String, String> calculationData) throws IncorrectInputException {
        String orderType = calculationData.get(ORDER_TYPE);
        String orderWeight = calculationData.get(ORDER_WEIGHT);
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);
        String firstCity = calculationData.get(FIRST_CITY);
        String secondCity = calculationData.get(SECOND_CITY);
        String deliveryType = calculationData.get(DELIVERY_TYPE);

        return PriceCalculatorByOrderType.calculateDeliveryPrice(
                orderType,
                orderWeight,
                orderLength,
                orderHeight,
                orderWidth,
                firstCity,
                secondCity,
                deliveryType);
    }

    /**
     * Method calculateTotalPrice used to calculate total price of delivery service.
     *
     * @param calculationData data from order creation page
     * @return order volume value.
     */
    static BigDecimal calculateTotalPrice(Map<String, String> calculationData) throws IncorrectInputException {
        String orderType = calculationData.get(ORDER_TYPE);
        String orderPrice = calculationData.get(ORDER_PRICE);
        String orderWeight = calculationData.get(ORDER_WEIGHT);
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);
        String firstCity = calculationData.get(FIRST_CITY);
        String secondCity = calculationData.get(SECOND_CITY);
        String deliveryType = calculationData.get(DELIVERY_TYPE);

        return TotalDeliveryPriceCalculator.calculateTotalPriceOfDeliveryService(
                orderType,
                orderPrice,
                orderWeight,
                orderLength,
                orderHeight,
                orderWidth,
                firstCity,
                secondCity,
                deliveryType);
    }

    /**
     * Method isInvoiceDataValid used to validation input data
     *
     * @param invoiceData data for validation from request
     * @return true if data valid and false otherwise
     */
    private boolean isInvoiceDataValid(Map<String, String> invoiceData) {
        boolean isValid = false;

        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_PRICE))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_WEIGHT))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_LENGTH))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_HEIGHT))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_WIDTH))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(DELIVERY_DISTANCE))) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Method createUser used to set user id.
     *
     * @param id user id that will be set.
     * @return existed user, that has only id.
     */
    private User createUser(String id) {
        long userId = Long.parseLong(id);
        return new User.Builder()
                .setUserId(userId)
                .build();
    }

    /**
     * Method createOrder used to set order id.
     *
     * @param id order id that will be set.
     * @return existed order, that has only id.
     */
    private Order createOrder(String id) {
        long orderId = Long.parseLong(id);
        return new Order.Builder()
                .setOrderId(orderId)
                .build();
    }

    /**
     * Method createDelivery used to set delivery id.
     *
     * @param id delivery id that will be set.
     * @return existed delivery, that has only id.
     */
    private Delivery createDelivery(String id) {
        long deliveryId = Long.parseLong(id);
        return new Delivery.Builder()
                .setDeliveryId(deliveryId)
                .build();
    }

    /**
     * Method createFirstAddress used to set first address id.
     *
     * @param id first address id that will be set.
     * @return existed first address, that has only id.
     */
    private AddressFirst createFirstAddress(String id) {
        long firstAddressId = Long.parseLong(id);
        return new AddressFirst.Builder()
                .setFirstAddressId(firstAddressId)
                .build();
    }

    /**
     * Method createSecondAddress used to set second address id.
     *
     * @param id second address id that will be set.
     * @return existed second address, that has only id.
     */
    private AddressSecond createSecondAddress(String id) {
        long secondAddressId = Long.parseLong(id);
        return new AddressSecond.Builder()
                .setSecondAddressId(secondAddressId)
                .build();
    }
}
