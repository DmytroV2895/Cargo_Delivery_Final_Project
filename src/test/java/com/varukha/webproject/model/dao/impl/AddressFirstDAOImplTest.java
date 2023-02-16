package com.varukha.webproject.model.dao.impl;

import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static com.varukha.webproject.model.dao.impl.DAOTestUtil.getTestAddressFirst;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;


class AddressFirstDAOImplTest {

    @Test
    void testAddFirstAddress() throws SQLException {
       ConnectionPool connectionPool = mock(ConnectionPool.class);
       AddressFirstDAO addressFirstDAO = new AddressFirstDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            when(connectionPool.getConnection().prepareStatement(SQL_Queries.ADD_FIRST_ADDRESS, Statement.RETURN_GENERATED_KEYS)).thenReturn(ignored);
            ResultSet resultSet = mock(ResultSet.class);
            when(ignored.getGeneratedKeys()).thenReturn(resultSet);
            assertDoesNotThrow(() -> addressFirstDAO.addFirstAddress(getTestAddressFirst()));
        }
    }

    @Test
    void testUpdateFirstAddress() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        AddressFirstDAO addressFirstDAO = new AddressFirstDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return addressFirstDAO.updateFirstAddressData(getTestAddressFirst());
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



//    @Test
//    void testUpdateFirstAddress() throws SQLException {
//        ConnectionPool connectionPool = mock(ConnectionPool.class);
//        AddressFirstDAO addressFirstDAO = new AddressFirstDAOImpl(connectionPool);
//        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
//            assertTrue(() -> {
//                try {
//                    return addressFirstDAO.updateFirstAddressData(getTestAddressFirst());
//                } catch (DAOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//    }