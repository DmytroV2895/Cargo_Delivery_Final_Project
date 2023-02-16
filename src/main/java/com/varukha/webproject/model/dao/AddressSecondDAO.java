package com.varukha.webproject.model.dao;


import com.varukha.webproject.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AddressDAO interface
 *
 * @author Dmytro Varukha
 *
 */


public interface AddressSecondDAO {


    /**
     * Add address
     * @param addressSecond
     * @return boolean result of operation
     * @throws DAOException
     */
    long addSecondAddress(AddressSecond addressSecond) throws DAOException;



    /**
     * Update second address data in database
     * @param data to update second address information
     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    boolean updateSecondAddressData(AddressSecond data) throws DAOException;

}
