package com.varukha.webproject.model.dao;


import com.varukha.webproject.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * AddressFirstDAO interface
 *
 * @author Dmytro Varukha
 *
 */

public interface AddressFirstDAO {


    /**
     * Add new sender address to the database
     * @param firstAddressSecond information about first address
     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    long addFirstAddress(AddressFirst firstAddressSecond) throws DAOException;


    /**
     * Update first address data in the database
     * @param data to update first address information
     * @return boolean result of updating operation
     * @throws DAOException is wrapper for SQLException
     */
    boolean updateFirstAddressData(AddressFirst data) throws DAOException;
}
