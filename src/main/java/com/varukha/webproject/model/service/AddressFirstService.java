package com.varukha.webproject.model.service;


import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import java.sql.SQLException;
import java.util.Map;

public interface AddressFirstService {

    /**
     * Save new first address to database
     *
     * @param addressData
     * @return boolean result of operation
     * @throws SQLException
     */
    long addAddressFirst(Map<String, String> addressData) throws ServiceException, IncorrectInputException;

    /**
     * Update first address data in database
     * @param data to update current first address information
     * @return boolean result of operation
     * @throws ServiceException include DAOException and can be thrown by another mistakes
     * @throws IncorrectInputException thrown when incorrect numbers data for calculations
     */
    boolean updateAddressFirst(Map<String, String> data) throws ServiceException, IncorrectInputException;
}
