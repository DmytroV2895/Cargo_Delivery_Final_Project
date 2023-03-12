package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.Delivery;
import com.varukha.webproject.exception.DAOException;

/**
 * Interface DeliveryDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface DeliveryDAO {

    /**
     * Method addDelivery used to adding new delivery information about order to database.
     *
     * @param delivery contain delivery data that will be added to database.
     * @return id of delivery information that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    long addDelivery(Delivery delivery) throws DAOException;

    /**
     * Method updateDeliveryData used to updating delivery information.
     *
     * @param data contain a set of data to change delivery information.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateDeliveryData(Delivery data) throws DAOException;

}
