package com.varukha.webproject.model.dao;


import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DeliveryDAO interface
 *
 * @author Dmytro Varukha
 *
 */

public interface DeliveryDAO {

    /**
     * Add delivery
     *
     * @param delivery
     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    long addDelivery(Delivery delivery) throws DAOException;


    /**
     * Update delivery data in database
     * @param data to update
     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    boolean updateDeliveryData(Delivery data) throws DAOException;

}
