package com.varukha.webproject.model.dao.impl;

import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.InvoiceDAO;
import com.varukha.webproject.model.entity.Invoice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.varukha.webproject.model.entity.Delivery.DeliveryType.BY_TRUCK;
import static com.varukha.webproject.model.entity.Invoice.OrderStatus.ON_THE_WAY;
import static com.varukha.webproject.model.entity.Order.Type.CARGO;
import static com.varukha.webproject.model.dao.ColumnName.*;
import static com.varukha.webproject.model.dao.impl.DAOTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class InvoiceDAOImplTest {

    private static final List<Invoice> invoiceList = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        invoiceList.add(getTestInvoice());
    }
    @Test
    void testAddInvoice() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertDoesNotThrow(() -> invoiceDAO.addInvoice(getTestInvoice()));
        }
    }

    @Test
    void testFindInvoiceById() throws SQLException, DAOException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl(connectionPool);
        try (PreparedStatement preparedStatement = prepareMocks(connectionPool)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Invoice resultInvoice  = invoiceDAO.findInvoiceById(1L).orElse(null);
            assertNotNull(resultInvoice);
            assertEquals(getTestInvoice(), resultInvoice);
            assertEquals(getTestInvoice().toString(), resultInvoice.toString());
        }
    }

    @Test
    void testFindInvoiceByDeliveryDate() throws SQLException, DAOException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl(connectionPool);
        try (PreparedStatement preparedStatement = prepareMocks(connectionPool)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Invoice resultInvoice  = invoiceDAO.findInvoicesByDeliveryDate("2023.10.12").orElse(null);
            assertNotNull(resultInvoice);
            assertEquals(getTestInvoice(), resultInvoice);
            assertEquals(getTestInvoice().toString(), resultInvoice.toString());
        }
    }

    @Test
    void testFindInvoiceByDestinationCity() throws SQLException, DAOException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl(connectionPool);
        try (PreparedStatement preparedStatement = prepareMocks(connectionPool)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Invoice> resultInvoice  = invoiceDAO.findInvoiceByDestinationCity("SUMY", "ODESSA");
            assertNotNull(resultInvoice);
            assertEquals(invoiceList, resultInvoice);

        }
    }

    @Test
    void testUpdateDeliveryPaymentStatusByInvoiceId() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return invoiceDAO.updateDeliveryPaymentStatusByInvoiceId(getTestInvoice().getOrder().getOrderId());
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private PreparedStatement prepareMocks(ConnectionPool connectionPool) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setBigDecimal(isA(int.class), isA(BigDecimal.class));
        doNothing().when(preparedStatement).setBigDecimal(isA(int.class), isA(BigDecimal.class));
        doNothing().when(preparedStatement).setBoolean(isA(int.class), isA(boolean.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        when(preparedStatement.executeUpdate()).thenReturn(1);
        return preparedStatement;
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong(ID_INVOICE)).thenReturn(1L);
        when(resultSet.getBigDecimal(DELIVERY_PRICE)).thenReturn(BigDecimal.valueOf(600));
        when(resultSet.getBigDecimal(TOTAL_PRICE)).thenReturn(BigDecimal.valueOf(1500));
        when(resultSet.getBoolean(DELIVERY_PAID_STATUS)).thenReturn(true);
        when(resultSet.getString(ORDER_STATUS)).thenReturn(String.valueOf(ON_THE_WAY));

        when(resultSet.getLong(ID_DELIVERY)).thenReturn(1L);
        when(resultSet.getString(DELIVERY_TYPE)).thenReturn(String.valueOf(BY_TRUCK));
        when(resultSet.getInt(DELIVERY_DISTANCE)).thenReturn(1000);
        when(resultSet.getString(RECIPIENT_NAME)).thenReturn("Oleksandr");
        when(resultSet.getString(RECIPIENT_SURNAME)).thenReturn("Pruvalov");
        when(resultSet.getString(RECIPIENT_PHONE)).thenReturn("+380668774412");

        when(resultSet.getLong(ID_USER)).thenReturn(1L);
        when(resultSet.getString(USER_NAME)).thenReturn("Victor");
        when(resultSet.getString(USER_SURNAME)).thenReturn("Bondarenko");
        when(resultSet.getString(USER_EMAIL)).thenReturn("victor@gmail.com");
        when(resultSet.getString(USER_PHONE)).thenReturn("+380668996655");
        when(resultSet.getBigDecimal(USER_PAYMENT_ACCOUNT)).thenReturn(BigDecimal.valueOf(0));

        when(resultSet.getLong(ID_ORDER)).thenReturn(1L);
        when(resultSet.getString(ORDER_NAME)).thenReturn("Computer");
        when(resultSet.getString(ORDER_TYPE)).thenReturn(String.valueOf(CARGO));
        when(resultSet.getString(ORDER_DESCRIPTION)).thenReturn("New computer");
        when(resultSet.getBigDecimal(ORDER_PRICE)).thenReturn(BigDecimal.valueOf(15000));
        when(resultSet.getDouble(ORDER_WEIGHT)).thenReturn(50.0);
        when(resultSet.getDouble(ORDER_LENGTH)).thenReturn(34.0);
        when(resultSet.getDouble(ORDER_HEIGHT)).thenReturn(67.0);
        when(resultSet.getDouble(ORDER_WIDTH)).thenReturn(45.0);
        when(resultSet.getDouble(ORDER_VOLUME)).thenReturn(1025.1);
        when(resultSet.getDouble(ORDER_VOLUME_WEIGHT)).thenReturn(50.0);

        when(resultSet.getLong(ID_FIRST_ADDRESS)).thenReturn(1L);
        when(resultSet.getString(FIRST_CITY)).thenReturn("SUMY");
        when(resultSet.getString(FIRST_STREET_NAME)).thenReturn("Mira");
        when(resultSet.getString(FIRST_STREET_NUMBER)).thenReturn("25");
        when(resultSet.getString(FIRST_HOUSE_NUMBER)).thenReturn("56");

        when(resultSet.getLong(ID_SECOND_ADDRESS)).thenReturn(1L);
        when(resultSet.getString(SECOND_CITY)).thenReturn("CHARKIV");
        when(resultSet.getString(SECOND_STREET_NAME)).thenReturn("Lisna");
        when(resultSet.getString(SECOND_STREET_NUMBER)).thenReturn("35");
        when(resultSet.getString(SECOND_HOUSE_NUMBER)).thenReturn("85");
    }
}