package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.entity.Order;
import com.varukha.webproject.entity.Order.*;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.OrderDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;

import static com.varukha.webproject.model.dao.ColumnName.*;

/**
 * Class OrderDAO
 * @author Dmytro Varukha
 */

public class OrderDAOImpl implements OrderDAO {
    private final ConnectionPool connectionPool;
    private static final Logger logger = LogManager.getLogger();
    public OrderDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Order createOrder(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating new order");
        long orderId = resultSet.getLong(ID_ORDER);
        String name = resultSet.getString(ORDER_NAME);
        Type orderType = Type.valueOf(resultSet.getString(ORDER_TYPE));
        String orderDescription = resultSet.getString(ORDER_DESCRIPTION);
        BigDecimal price = resultSet.getBigDecimal(ORDER_PRICE);
        double weight = resultSet.getDouble(ORDER_WEIGHT);
        double length = resultSet.getDouble(ORDER_LENGTH);
        double height = resultSet.getDouble(ORDER_HEIGHT);
        double width = resultSet.getDouble(ORDER_WIDTH);
        double volume = resultSet.getDouble(ORDER_VOLUME);
        double volumeWeight = resultSet.getDouble(ORDER_VOLUME_WEIGHT);
        logger.log(Level.DEBUG, "orderId: " + orderId + " order name: " + name);
        Order order = new Builder()
                .setOrderId(orderId)
                .setOrderName(name)
                .setOrderType(orderType)
                .setOrderDescription(orderDescription)
                .setOrderPrice(price)
                .setOrderWeight(weight)
                .setOrderLength(length)
                .setOrderHeight(height)
                .setOrderWidth(width)
                .setOrderVolume(volume)
                .setOrderVolumeWeight(volumeWeight)
                .build();
        return order;
    }

    @Override
    public long addOrder(Order order) throws DAOException {
        logger.log(Level.INFO, "Trying to add new order to database: " + order);
        long orderId = 0;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(++k, order.getOrderName());
            st.setString(++k, String.valueOf(order.getType()));
            st.setString(++k, order.getOrderDescription());
            st.setBigDecimal(++k, order.getPrice());
            st.setDouble(++k, order.getWeight());
            st.setDouble(++k, order.getLength());
            st.setDouble(++k, order.getHeight());
            st.setDouble(++k, order.getWidth());
            st.setDouble(++k, order.getVolume());
            st.setDouble(++k, order.getVolumeWeight());
            st.setLong(++k, order.getUserId().getUserId());
            st.setLong(++k, order.getDeliveryId().getDeliveryId());
            int countRow = st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                orderId = resultSet.getLong(1);
                logger.log(Level.INFO, "Order: " + order + " was successfully added to database.");
            } else {
                logger.log(Level.ERROR, "Order: " + order + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addOrder method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in addOrder method" + order, e);
        }
        return orderId;
    }

    @Override
    public boolean updateOrderData(Order data) throws DAOException {
        logger.log(Level.INFO, "Change order data in db" + data);
        boolean isChanged = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_ORDER_DATA)) {
            st.setString(++k, data.getOrderName());
            st.setString(++k, String.valueOf(data.getType()));
            st.setString(++k, data.getOrderDescription());
            st.setBigDecimal(++k, data.getPrice());
            st.setDouble(++k, data.getWeight());
            st.setDouble(++k, data.getLength());
            st.setDouble(++k, data.getHeight());
            st.setDouble(++k, data.getWidth());
            st.setDouble(++k, data.getVolume());
            st.setDouble(++k, data.getVolumeWeight());

            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                logger.log(Level.INFO, "Order data were changed. New data:" + data);
            } else {
                logger.log(Level.ERROR, "Order data were not changed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL EXCEPTION " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException(
                    "DAO exception in changeOrderData method" + data, e);
        }
        return isChanged;
    }
}







