package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.Order;
import com.varukha.webproject.exception.DAOException;


/**
 * Interface OrderDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface OrderDAO {

    /**
     * Method addOrder used to adding new order to database.
     *
     * @param order contain order data that will be added to database.
     * @return id of order that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    long addOrder(Order order) throws DAOException;

    /**
     * Method updateOrderData used to updating order data.
     *
     * @param data contain a set of data to change order information.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateOrderData(Order data) throws DAOException;

}
