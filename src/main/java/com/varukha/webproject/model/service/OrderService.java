package com.varukha.webproject.model.service;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.util.Map;

/**
 * Interface OrderService contain contracts of service methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public interface OrderService {

    /**
     * Method addOrder used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new order data to database.
     *
     * @param data contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    long addOrder(Map<String, String> data) throws ServiceException, IncorrectInputException;

    /**
     * Method updateOrder is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update order data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean updateOrder(Map<String, String> data) throws ServiceException, IncorrectInputException;

}

