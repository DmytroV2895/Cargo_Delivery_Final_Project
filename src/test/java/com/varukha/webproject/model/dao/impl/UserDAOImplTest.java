package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.UserDAO;
import com.varukha.webproject.util.Encoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;

import static com.varukha.webproject.model.dao.ColumnName.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class UserDAOImplTest {

   private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User.Builder()
                .setName("Victor")
                .setSurname("Bondarenko")
                .setEmail("victor@gmail.com")
                .setPhone("+380668996655")
                .setRole(User.Role.USER)
                .setPaymentAccount(BigDecimal.valueOf(0))
                .build();
    }


    @Test
    void findUserByEmail() throws SQLException, DAOException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        UserDAO userDAO = new UserDAOImpl(connectionPool);
        try (PreparedStatement preparedStatement = prepareMocks(connectionPool)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDAO.findUserByEmail("victor@gmail.com").orElse(null);
            assertNotNull(resultUser);
            assertEquals(user, resultUser);
        }
    }

    @Test
    void testAddUser() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        UserDAO userDAO = new UserDAOImpl(connectionPool);
        String encodedPassword = Encoder.encodePassword("Qaz123Zaq");
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return userDAO.addUser(user, encodedPassword);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Test
    void testAddMoneyToUserAccount() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        UserDAO userDAO = new UserDAOImpl(connectionPool);
        String moneySum = "100";
        long userId = 1L;
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return userDAO.topUpUserAccount(userId, moneySum);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Test
    void testPayForDeliveryService() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        UserDAO userDAO = new UserDAOImpl(connectionPool);
        String totalPrice = "100";
        long userId = 1L;
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return userDAO.payForDeliveryService(totalPrice, userId);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private PreparedStatement prepareMocks(ConnectionPool connectionPool) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setBigDecimal(isA(int.class), isA(BigDecimal.class));
        when(ps.executeUpdate()).thenReturn(1);
        return ps;
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(USER_NAME)).thenReturn("Victor");
        when(resultSet.getString(USER_SURNAME)).thenReturn("Bondarenko");
        when(resultSet.getString(USER_EMAIL)).thenReturn("victor@gmail.com");
        when(resultSet.getString(USER_PHONE)).thenReturn("+380668996655");
        when(resultSet.getString(USER_ROLE)).thenReturn(String.valueOf(User.Role.USER));
        when(resultSet.getBigDecimal(USER_PAYMENT_ACCOUNT)).thenReturn(BigDecimal.valueOf(0));
    }
}