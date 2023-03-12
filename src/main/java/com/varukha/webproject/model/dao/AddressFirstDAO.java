package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;

/**
 * Interface AddressFirstDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface AddressFirstDAO {

    /**
     * Method addFirstAddress used to adding first address to database.
     *
     * @param addressFirst contain first address data that will be added to database.
     * @return id of first address that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    long addFirstAddress(AddressFirst addressFirst) throws DAOException;

    /**
     * Method updateFirstAddressData used to updating first address data.
     *
     * @param data contain a set of data to change first address information.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean updateFirstAddressData(AddressFirst data) throws DAOException;
}
