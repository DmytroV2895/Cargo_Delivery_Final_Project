package com.varukha.webproject.model.service;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.util.Map;


public interface DeliveryService {



    /**
     * Add delivery information
     * @param data
     * @return boolean result of operation
     * @throws ServiceException
     */
    long addDelivery(Map<String,String> data) throws ServiceException, IncorrectInputException;


    /**
     * Update delivery data in database
     * @param data to update current delivery information
     * @return boolean result of operation
     * @throws ServiceException
     * @throws IncorrectInputException
     */
    boolean updateDelivery(Map<String, String> data) throws ServiceException, IncorrectInputException;

}
