package com.varukha.webproject.model.service;

import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.sql.SQLException;
import java.util.Map;




public interface AddressSecondService {

    /**
     * Save new second address to database
     *
     * @param addressData
     * @return boolean result of operation
     * @throws SQLException
     * @throws IncorrectInputException thrown when incorrect numbers data for calculations
     */
    long addAddressSecond(Map<String, String> addressData) throws ServiceException, IncorrectInputException;


    /**
     * Update second address data in database
     * @param data to update current second address information
     * @return boolean result of operation
     * @throws ServiceException include DAOException and can be thrown by another mistakes
     * @throws IncorrectInputException thrown when incorrect numbers data for calculations
     */
    boolean updateAddressSecond(Map<String, String> data) throws ServiceException, IncorrectInputException;

}
