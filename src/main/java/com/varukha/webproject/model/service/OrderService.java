package com.varukha.webproject.model.service;



import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.util.Map;


/**
 * The interface order service
 * @author Dmytro Varukha
 *
 */

public interface OrderService {

    /**
     * Add order new order
     * @param data about order
     * @return boolean result of operation
     * @throws DAOException
     */
    long addOrder(Map<String, String> data) throws ServiceException, IncorrectInputException;

    /**
     * Update order data in database
     * @param data to update current order information
     * @return boolean result of operation
     * @throws ServiceException
     * @throws IncorrectInputException
     */
    boolean updateOrder(Map<String, String> data) throws ServiceException, IncorrectInputException;

}

