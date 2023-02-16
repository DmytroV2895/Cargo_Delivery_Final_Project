package com.varukha.webproject.model.dao.impl;



import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.entity.Order;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.OrderDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.sql.*;


import static com.varukha.webproject.model.dao.impl.DAOTestUtil.getTestOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class OrderDAOImplTest {

   private static Order order;

    @BeforeAll
    public static void setUp() {

       User user = new User.Builder()
                .setUserId(1L)
                .build();

        Delivery delivery = new Delivery.Builder()
                .setDeliveryId(1L)
                .build();


         order = new Order.Builder()
                .setOrderId(1L)
                .setOrderName("Computer")
                .setOrderType(Order.Type.CARGO)
                .setOrderDescription("New computer")
                .setOrderPrice(BigDecimal.valueOf(15000))
                .setOrderWeight(50)
                .setOrderLength(34)
                .setOrderHeight(67)
                .setOrderWidth(45)
                .setOrderVolume(1025.1)
                .setOrderVolumeWeight(50)
               .setUserId(user)
               .setDeliveryId(delivery)
               .build();
    }

    @Test
    void testAddOrder() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        OrderDAO orderDAO = new OrderDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            when(connectionPool.getConnection().prepareStatement(SQL_Queries.ADD_ORDER, Statement.RETURN_GENERATED_KEYS)).thenReturn(ignored);
            ResultSet resultSet = mock(ResultSet.class);
            when(ignored.getGeneratedKeys()).thenReturn(resultSet);
            assertDoesNotThrow(() -> orderDAO.addOrder(order));
        }
    }

    @Test
    void testUpdateOrderData() throws SQLException {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        OrderDAO orderDAO = new OrderDAOImpl(connectionPool);
        try (PreparedStatement ignored = prepareMocks(connectionPool)) {
            assertTrue(() -> {
                try {
                    return orderDAO.updateOrderData(getTestOrder());
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
        doNothing().when(ps).setBigDecimal(isA(int.class), isA(BigDecimal.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setDouble(isA(int.class), isA(double.class));
        doNothing().when(ps).setLong(isA(int.class), isA(long.class));
        doNothing().when(ps).setLong(isA(int.class), isA(long.class));
        when(ps.executeUpdate()).thenReturn(1);
        return ps;
    }
}



