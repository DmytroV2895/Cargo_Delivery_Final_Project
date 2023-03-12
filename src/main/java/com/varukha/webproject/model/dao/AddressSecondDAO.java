package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;

/**
 * Interface AddressSecondDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface AddressSecondDAO {

    /**
     * Method addSecondAddress used to adding second address to database.
     *
     * @param addressSecond contain second address data that will be added to database.
     * @return id of second address that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    long addSecondAddress(AddressSecond addressSecond) throws DAOException;

    /**
     * Method updateSecondAddressData used to updating second address data.
     *
     * @param data contain a set of data to change second address information.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateSecondAddressData(AddressSecond data) throws DAOException;

}
