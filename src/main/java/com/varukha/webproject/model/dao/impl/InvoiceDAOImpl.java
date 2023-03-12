package com.varukha.webproject.model.dao.impl;

import com.varukha.webproject.model.entity.*;
import com.varukha.webproject.model.entity.Order.Type;
import com.varukha.webproject.model.entity.Delivery.DeliveryType;
import com.varukha.webproject.model.entity.Invoice.OrderStatus;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.InvoiceDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.varukha.webproject.model.dao.ColumnName.*;

/**
 * Class InvoiceDAO implements methods for interaction with MySQL database
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public class InvoiceDAOImpl implements InvoiceDAO {
    private final ConnectionPool connectionPool;
    private static final Logger logger = LogManager.getLogger();

    public InvoiceDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method addInvoice used to adding new invoice to database.
     *
     * @param invoice contain a set of data that will be added to database.
     * @return boolean result of operation. Return true if new invoice was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean addInvoice(Invoice invoice) throws DAOException {
        logger.log(Level.INFO, "Add invoice to database" + invoice);
        boolean isAdded = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_INVOICE)) {
            st.setBigDecimal(++k, invoice.getDeliveryPrice());
            st.setBigDecimal(++k, invoice.getTotalPrice());
            st.setBoolean(++k, invoice.getIsDeliveryPaid());
            st.setString(++k, String.valueOf(invoice.getOrderStatus()));
            st.setLong(++k, invoice.getDelivery().getDeliveryId());
            st.setLong(++k, invoice.getUser().getUserId());
            st.setLong(++k, invoice.getOrder().getOrderId());
            st.setLong(++k, invoice.getAddressFirst().getFirstAddressId());
            st.setLong(++k, invoice.getAddressSecond().getSecondAddressId());
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                logger.log(Level.INFO, "Invoice: " + invoice + " was successfully added to database");
            } else {
                logger.log(Level.ERROR, "Invoice: " + invoice + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addInvoice method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in addInvoice method" + invoice, e);
        }
        return isAdded;
    }

    /**
     * Method updateInvoiceData used to updating invoice data.
     *
     * @param data contain a set of data to change is performed.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean updateInvoiceData(Invoice data) throws DAOException, SQLException {
        logger.log(Level.INFO, "Update invoice in database" + data);
        boolean isAdded = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_INVOICE_DATA)) {
            st.setBigDecimal(++k, data.getDeliveryPrice());
            st.setBigDecimal(++k, data.getTotalPrice());
            st.setLong(++k, data.getInvoiceId());
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                logger.log(Level.INFO, "Invoice: " + data + " was successfully updated");
            } else {
                logger.log(Level.ERROR, "Invoice: " + data + " was not updated");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in updateInvoiceData method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in updateInvoiceData method" + data, e);
        }
        return isAdded;
    }

    /**
     * Method findInvoiceById used to find invoice by invoice id.
     *
     * @param invoiceId invoice id in which the search is performed.
     * @return optional result of operation. Return optional of invoice if available and return empty if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public Optional<Invoice> findInvoiceById(long invoiceId) throws DAOException {
        logger.log(Level.INFO, "Find invoice by Id: " + invoiceId);
        Optional<Invoice> invoiceById;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_INVOICE_BY_ID)) {
            st.setLong(1, invoiceId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                logger.log(Level.INFO, "Invoice by Id: " + invoiceId);
                Invoice invoice = createInvoice(resultSet);
                invoiceById = Optional.of(invoice);
            } else {
                logger.log(Level.INFO, "Didn't find invoice by id:" + invoiceId);
                invoiceById = Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException("DAO exception in findInvoiceById method. InvoiceId: " + invoiceId +
                    " error code: " + e.getErrorCode(), e);
        }
        return invoiceById;
    }

    /**
     * Method findInvoiceByDeliveryDate used to find all invoices by delivery date.
     *
     * @param deliveryDate date in which the search is performed.
     * @return optional result of operation. Return optional of invoices if available and return empty if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public Optional<Invoice> findInvoicesByDeliveryDate(String deliveryDate) throws DAOException {
        logger.log(Level.INFO, "Find invoice by delivery date: " + deliveryDate);
        Optional<Invoice> invoiceByDeliveryDate = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_INVOICE_DELIVERY_DATE)) {
            st.setString(1, deliveryDate);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoiceByDeliveryDate = Optional.of(invoice);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException("DAO exception in findInvoicesByDeliveryDate method. Delivery date " + deliveryDate +
                    " error code: " + e.getErrorCode(), e);
        }
        return invoiceByDeliveryDate;
    }

    /**
     * Method findInvoiceByDestinationCity used to find all invoice by sender city and recipient city.
     *
     * @param firstCity  sender citi city in which the search is performed.
     * @param secondCity receiver city in which the search will perform.
     * @return list of invoices by searching first city and second city.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public List<Invoice> findInvoiceByDestinationCity(String firstCity, String secondCity) throws DAOException {
        logger.log(Level.INFO, "Find invoice by destination. First city: " + firstCity + " Second city: " + secondCity);
        List<Invoice> invoiceByDestination = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_INVOICE_BY_DESTINATION)) {
            st.setString(1, firstCity);
            st.setString(2, secondCity);
            st.setString(3, secondCity);
            st.setString(4, firstCity);
            ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {
                    invoiceByDestination.add(createInvoice(resultSet));
                }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException("DAO exception in findInvoiceByDestinationCity method. First city: " + firstCity +
                    " Second city: " + secondCity + " error code: " + e.getErrorCode(), e);
        }
        return invoiceByDestination;
    }

    /**
     * Method updateDeliveryPaymentStatusByInvoiceId used to update delivery payment status by invoice id.
     *
     * @param invoiceId invoice id in which update is performed.
     * @return boolean result of operation of updating delivery payment status.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean updateDeliveryPaymentStatusByInvoiceId(long invoiceId) throws DAOException {
        logger.log(Level.INFO, "Update delivery payment status by invoiceId: " + invoiceId);
        boolean isUpdated = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_DELIVERY_PAYMENT_STATUS)) {
            st.setLong(++k, invoiceId);
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isUpdated = true;
                logger.log(Level.INFO, "Delivery payment status was updated successfully");
            } else {
                logger.log(Level.ERROR, "Delivery payment status was not updated");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in updateDeliveryPaymentStatusByInvoiceId method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in updateDeliveryPaymentStatusByInvoiceId method ", e);
        }
        return isUpdated;
    }

    /**
     * Method updateInvoiceDeliveryDateAndOrderStatus used to update delivery date and order status in invoice table.
     *
     * @param invoice contain a set of information about invoice that used to updating delivery date and order status.
     * @return boolean result of operation of updating delivery date and order status.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean updateInvoiceDeliveryDateAndOrderStatus(Invoice invoice) throws DAOException {
        logger.log(Level.INFO, "Change invoice delivery data and order status by manager " + invoice);
        boolean isChanged = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.CHANGE_INVOICE_MANAGER)) {
            st.setDate(++k, invoice.getDeliveryDate());
            st.setString(++k, String.valueOf(invoice.getOrderStatus()));
            st.setLong(++k, invoice.getInvoiceId());
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                logger.log(Level.INFO, "Invoice data was changed successfully: " + invoice);
            } else {
                logger.log(Level.ERROR, "Invoice data was not changed");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception in updateInvoiceDeliveryDateAndOrderStatus method " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException("DAO exception in updateInvoiceDeliveryDateAndOrderStatus method during data changing process:" + invoice, e);
        }
        return isChanged;
    }

    /**
     * Method findNumberOfRecordsInInvoiceTable used to find number of records in invoice table.
     *
     * @return number of records in invoice table.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public int findNumberOfRecordsInInvoiceTable() throws DAOException {
        logger.log(Level.INFO, "Find number of records in the database invoice table");
        int numberOfRows = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.COUNT_ALL_INVOICES)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findNumberOfRecordsInInvoiceTable: " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in findNumberOfRecordsInInvoiceTable method ", e);
        }
        return numberOfRows;
    }

    /**
     * Method findNumberOfRecordsInInvoiceTableByUserId used to find number of records in invoice table by user id.
     *
     * @param userId user id in which the search is performed.
     * @return number of rows in invoice table by user id.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public int findNumberOfRecordsInInvoiceTableByUserId(long userId) throws DAOException {
        logger.log(Level.INFO, "Find number of records in the database invoice table by userId");
        int numberOfRows = 0;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.COUNT_ALL_INVOICES_BY_USER_ID)) {
            st.setLong(++k, userId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findNumberOfRecordsInInvoiceTableByUserId: " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in findNumberOfRecordsInInvoiceTableByUserId method ", e);
        }
        return numberOfRows;
    }

    /**
     * Method findAllInvoicesByDeliveryDate used to find all invoices with orders' information order by delivery date.
     *
     * @param numberOfDataOnPage the number of displayed records.
     * @param dataFromRow        the number rows in invoice table.
     * @return list of all invoice from database.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public List<Invoice> findAllInvoicesByDeliveryDate(int dataFromRow, int numberOfDataOnPage) throws DAOException {
        logger.log(Level.INFO, "Find all invoices from database order by delivery date");
        int k = 0;
        List<Invoice> invoices = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_INVOICES_FROM_ROW)) {
            st.setInt(++k, dataFromRow);
            st.setInt(++k, numberOfDataOnPage);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Invoice order = createInvoice(resultSet);
                invoices.add(order);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findAllInvoicesByDeliveryDate: " + e.getMessage() + " : " + e.getErrorCode());
            throw new DAOException("DAO exception in findAllInvoicesByDeliveryDate method ", e);
        }
        return invoices;
    }

    /**
     * Method findAllOrdersInfoFromInvoiceByUserIdFromRow used to find all orders information from invoice by user id.
     *
     * @param userId             user id in which the search is performed.
     * @param numberOfDataOnPage the number of displayed records.
     * @param fromRow            the number rows in invoice table.
     * @return list of all orders information from invoice by user id from database.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public List<Invoice> findAllOrdersInfoFromInvoiceByUserId(long userId, int numberOfDataOnPage, int fromRow) throws DAOException {
        logger.log(Level.INFO, "Find all order info from invoice by userId : " + userId);
        List<Invoice> invoiceList = new ArrayList<>();
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_ALL_INVOICE_DATA_BY_USER_ID_FROM_ROW)) {
            st.setLong(++k, userId);
            st.setInt(++k, fromRow);
            st.setInt(++k, numberOfDataOnPage);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findAllOrdersInfoFromInvoiceByUserId method:  " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method findAllOrdersInfoFromInvoiceByUserId. User id: " + userId + " error code: ", e);
        }
        return invoiceList;
    }

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
    @Override
    public List<Invoice> findAllOrdersInfoFromInvoiceByDeliveryCity(long userId, String deliveryCity, int numberOfDataOnPage, int fromRow) throws DAOException {
        logger.log(Level.INFO, "Find all order info from invoice by delivery city : " + deliveryCity);
        List<Invoice> invoiceList = new ArrayList<>();
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.FIND_INVOICES_FROM_ROW_BY_DELIVERY_CITY)) {
            st.setLong(++k, userId);
            st.setString(++k, deliveryCity);
            st.setInt(++k, fromRow);
            st.setInt(++k, numberOfDataOnPage);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findAllOrdersInfoFromInvoiceByDeliveryCity method:  " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method findAllOrdersInfoFromInvoiceByDeliveryCity. User id: " + userId + " error code: ", e);
        }
        return invoiceList;
    }

    /**
     * Method findNumberOfRecordsInInvoiceTableByDeliveryCity used to find number of records in invoice table by delivery city.
     *
     * @param userId       user id in which the search is performed.
     * @param deliveryCity delivery city in which the search is performed.
     * @return number of rows in invoice table by delivery city.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public int findNumberOfRecordsInInvoiceTableByDeliveryCity(long userId, String deliveryCity) throws DAOException {
        logger.log(Level.INFO, "Find number of records in the database invoice table by delivery city");
        int numberOfRows = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.COUNT_ALL_INVOICES_BY_DELIVERY_CITY)) {
            st.setLong(1, userId);
            st.setString(2, deliveryCity);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findNumberOfRecordsInInvoiceTableByDeliveryCity: " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in findNumberOfRecordsInInvoiceTableByDeliveryCity method ", e);
        }
        return numberOfRows;
    }

    /**
     * Method createUser used to create set of data about sender person.
     *
     * @param resultSet contain a set of information about sender person.
     * @return existed user information  from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    private User createUser(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating new user");
        return new User.Builder()
                .setUserId(resultSet.getLong(ID_USER))
                .setName(resultSet.getString(USER_NAME))
                .setSurname(resultSet.getString(USER_SURNAME))
                .setEmail(resultSet.getString(USER_EMAIL))
                .setPhone(resultSet.getString(USER_PHONE))
                .setPaymentAccount(resultSet.getBigDecimal(USER_PAYMENT_ACCOUNT))
                .build();
    }

    /**
     * Method createOrder used to create set of data about created order.
     *
     * @param resultSet contain a set of information about created order.
     * @return existed order information  from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    private Order createOrder(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating new order");
        return new Order.Builder()
                .setOrderId(resultSet.getLong(ID_ORDER))
                .setOrderName(resultSet.getString(ORDER_NAME))
                .setOrderType(Type.valueOf(resultSet.getString(ORDER_TYPE)))
                .setOrderDescription(resultSet.getString(ORDER_DESCRIPTION))
                .setOrderPrice(resultSet.getBigDecimal(ORDER_PRICE))
                .setOrderWeight(resultSet.getDouble(ORDER_WEIGHT))
                .setOrderLength(resultSet.getDouble(ORDER_LENGTH))
                .setOrderHeight(resultSet.getDouble(ORDER_HEIGHT))
                .setOrderWidth(resultSet.getDouble(ORDER_WIDTH))
                .setOrderVolume(resultSet.getDouble(ORDER_VOLUME))
                .setOrderVolumeWeight(resultSet.getDouble(ORDER_VOLUME_WEIGHT))
                .build();
    }

    /**
     * Method createDelivery used to create set of data about delivery.
     *
     * @param resultSet contain a set of information about delivery.
     * @return existed delivery information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    private Delivery createDelivery(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating new delivery");
        return new Delivery.Builder()
                .setDeliveryId(resultSet.getLong(ID_DELIVERY))
                .setDeliveryType(DeliveryType.valueOf(resultSet.getString(DELIVERY_TYPE)))
                .setDeliveryDistance(resultSet.getInt(DELIVERY_DISTANCE))
                .setRecipientName(resultSet.getString(RECIPIENT_NAME))
                .setRecipientSurname(resultSet.getString(RECIPIENT_SURNAME))
                .setRecipientPhone(resultSet.getString(RECIPIENT_PHONE))
                .build();
    }

    /**
     * Method createAddressFirst used to create set of data about first address.
     *
     * @param resultSet contain a set of information about sender address.
     * @return existed sender address information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    private AddressFirst createAddressFirst(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating first address");
        return new AddressFirst.Builder()
                .setFirstAddressId(resultSet.getLong(ID_FIRST_ADDRESS))
                .setFirstCity(resultSet.getString(FIRST_CITY))
                .setFirstStreetName(resultSet.getString(FIRST_STREET_NAME))
                .setFirstStreetNumber(resultSet.getString(FIRST_STREET_NUMBER))
                .setFirstHouseNumber(resultSet.getString(FIRST_HOUSE_NUMBER))
                .build();
    }

    /**
     * Method createAddressSecond used to create set of data about receiver address.
     *
     * @param resultSet contain a set of information about receiver address.
     * @return existed receiver address information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    private AddressSecond createAddressSecond(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating second address");
        return new AddressSecond.Builder()
                .setSecondAddressId(resultSet.getLong(ID_SECOND_ADDRESS))
                .setSecondCity(resultSet.getString(SECOND_CITY))
                .setSecondStreetName(resultSet.getString(SECOND_STREET_NAME))
                .setSecondStreetNumber(resultSet.getString(SECOND_STREET_NUMBER))
                .setSecondHouseNumber(resultSet.getString(SECOND_HOUSE_NUMBER))
                .build();
    }

    /**
     * Method createInvoice used to create set of invoice data with all information about user order.
     *
     * @param resultSet set of data about user order from database.
     * @return invoice information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    @Override
    public Invoice createInvoice(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating invoice");
        User user = createUser(resultSet);
        Order order = createOrder(resultSet);
        Delivery delivery = createDelivery(resultSet);
        AddressFirst addressFirst = createAddressFirst(resultSet);
        AddressSecond addressSecond = createAddressSecond(resultSet);
        return new Invoice.Builder()
                .setInvoiceId(resultSet.getLong(ID_INVOICE))
                .setDeliveryDate(resultSet.getDate(DELIVERY_DATE))
                .setDeliveryPrice(resultSet.getBigDecimal(DELIVERY_PRICE))
                .setTotalPrice(resultSet.getBigDecimal(TOTAL_PRICE))
                .setInvoiceCreationDateTime(resultSet.getTimestamp(INVOICE_CREATION_DATE_TIME))
                .setDeliveryPaymentStatus(resultSet.getBoolean(DELIVERY_PAID_STATUS))
                .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS)))
                .setDelivery(delivery)
                .setUser(user)
                .setOrder(order)
                .setFirstAddress(addressFirst)
                .setSecondAddress(addressSecond)
                .build();
    }
}
