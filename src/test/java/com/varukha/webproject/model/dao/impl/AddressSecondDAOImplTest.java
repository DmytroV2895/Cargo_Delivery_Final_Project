package com.varukha.webproject.model.dao.impl;

import com.varukha.webproject.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.dao.AddressSecondDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static com.varukha.webproject.model.dao.impl.DAOTestUtil.getTestAddressFirst;
import static com.varukha.webproject.model.dao.impl.DAOTestUtil.getTestAddressSecond;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AddressSecondDAOImplTest {


    @Test
    void testAddSecondAddress() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        AddressSecondDAO addressSecondDAO = new AddressSecondDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            when(connectionPool.getConnection().prepareStatement(SQL_Queries.ADD_SECOND_ADDRESS, Statement.RETURN_GENERATED_KEYS)).thenReturn(ignored);
            ResultSet resultSet = mock(ResultSet.class);
            when(ignored.getGeneratedKeys()).thenReturn(resultSet);
            assertDoesNotThrow(() -> addressSecondDAO.addSecondAddress(getTestAddressSecond()));
        }
    }

    @Test
    void testUpdateSecondAddress() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        AddressSecondDAO addressSecondDAO = new AddressSecondDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return addressSecondDAO.updateSecondAddressData(getTestAddressSecond());
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
        when(ps.executeUpdate()).thenReturn(1);
        return ps;
    }
}