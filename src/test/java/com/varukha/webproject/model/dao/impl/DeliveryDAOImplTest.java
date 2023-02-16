package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.DeliveryDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static com.varukha.webproject.model.dao.impl.DAOTestUtil.getTestDelivery;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class DeliveryDAOImplTest {


    @Test
    void testAddDelivery() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        DeliveryDAO deliveryDAO = new DeliveryDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            when(connectionPool.getConnection().prepareStatement(SQL_Queries.ADD_DELIVERY, Statement.RETURN_GENERATED_KEYS)).thenReturn(ignored);
            ResultSet resultSet = mock(ResultSet.class);
            when(ignored.getGeneratedKeys()).thenReturn(resultSet);
            assertDoesNotThrow(() -> deliveryDAO.addDelivery(getTestDelivery()));
        }
    }

    @Test
    void updateDeliveryData() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        DeliveryDAO deliveryDAO = new DeliveryDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return deliveryDAO.updateDeliveryData(getTestDelivery());
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
        doNothing().when(ps).setInt(isA(int.class), isA(int.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        doNothing().when(ps).setString(isA(int.class), isA(String.class));
        when(ps.executeUpdate()).thenReturn(1);
        return ps;
    }
}



