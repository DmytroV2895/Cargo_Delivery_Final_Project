package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.DeliveryDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class DeliveryDAOImpl implements DeliveryDAO {
    private static final Logger logger = LogManager.getLogger();
    private final ConnectionPool connectionPool;
    public DeliveryDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public long addDelivery(Delivery delivery) throws DAOException {
        logger.log(Level.INFO, "Add new delivery: " + delivery);
        long deliveryId = 0;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_DELIVERY, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(++k, String.valueOf(delivery.getType()));
            st.setInt(++k, delivery.getDeliveryDistance());
            st.setString(++k, delivery.getRecipientName());
            st.setString(++k, delivery.getRecipientSurname());
            st.setString(++k, delivery.getRecipientPhone());
            int rowCount = st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                deliveryId = resultSet.getLong(1);
                logger.log(Level.INFO, "Delivery: " + delivery + " was successfully added to database.");
            } else {
                logger.log(Level.ERROR, "Delivery: " + delivery + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL EXCEPTION " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException("DAO exception in addDelivery method when we try to add delivery:" + delivery, e);
        }
        return deliveryId;
    }


    @Override
    public boolean updateDeliveryData(Delivery data) throws DAOException {
        logger.log(Level.INFO, "Change delivery data in db" + data);
        boolean isChanged = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_DELIVERY_DATA)) {
            st.setString(++k, String.valueOf(data.getType()));
            st.setInt(++k, data.getDeliveryDistance());
            st.setString(++k, data.getRecipientName());
            st.setString(++k, data.getRecipientSurname());
            st.setString(++k, data.getRecipientPhone());

            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                logger.log(Level.INFO, "Delivery data were changed. New data:" + data);
            } else {
                logger.log(Level.ERROR, "Delivery data were not changed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL EXCEPTION " + e.getMessage() + "-" + e.getErrorCode());
            throw new DAOException(
                    "DAO exception in updateDeliveryData method" + data, e);
        }
        return isChanged;
    }

}

