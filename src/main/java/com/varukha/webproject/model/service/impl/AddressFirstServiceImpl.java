package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.model.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.service.AddressFirstService;
import com.varukha.webproject.util.validation.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class AddressFirstServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class AddressFirstServiceImpl implements AddressFirstService {

    private static final Logger logger = LogManager.getLogger();
    private final AddressFirstDAO addressFirstDAO;

    public AddressFirstServiceImpl(AddressFirstDAO addressFirstDAO) {
        this.addressFirstDAO = addressFirstDAO;
    }

    /**
     * Method addAddressFirst used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new first address to database.
     *
     * @param addressData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException        is wrapper for DAOException that throws exception during the runtime because of
     *                                 data validation fail or others mistakes.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    @Override
    public long addAddressFirst(Map<String, String> addressData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add first address. AddressData" + addressData);
        long addressFirstId = 0;
        isDataValid(addressData);

        AddressFirst addressFirst = new AddressFirst.Builder()
                .setFirstCity(addressData.get(FIRST_CITY))
                .setFirstStreetName(addressData.get(FIRST_STREET_NAME))
                .setFirstStreetNumber(addressData.get(FIRST_STREET_NUMBER))
                .setFirstHouseNumber(addressData.get(FIRST_HOUSE_NUMBER))
                .build();
        try {
            addressFirstId = addressFirstDAO.addFirstAddress(addressFirst);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in addFirstAddress method: " + e);
            throw new ServiceException(e);
        }
        return addressFirstId;
    }

    /**
     * Method updateAddressFirst is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update first address data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException        is wrapper for DAOException that throws exception during the runtime because of
     *                                 data validation fail or others mistakes.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    @Override
    public boolean updateAddressFirst(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add first address. AddressData" + data);
        boolean isChanged = false;
        isDataValid(data);

        AddressFirst addressFirst = new AddressFirst.Builder()
                .setFirstAddressId(Long.parseLong(data.get(FIRST_ADDRESS_ID)))
                .setFirstCity(data.get(FIRST_CITY))
                .setFirstStreetName(data.get(FIRST_STREET_NAME))
                .setFirstStreetNumber(data.get(FIRST_STREET_NUMBER))
                .setFirstHouseNumber(data.get(FIRST_HOUSE_NUMBER))
                .build();
        try {
            isChanged = addressFirstDAO.updateFirstAddressData(addressFirst);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in updateAddressFirst method: " + e);
            throw new ServiceException(e);
        }
        return isChanged;
    }

    /**
     * Method isDataValid used to validation input data
     *
     * @param data for validation from request
     */
    private void isDataValid(Map<String, String> data) throws IncorrectInputException {
        DataValidator.isTextValid(data.get(FIRST_CITY));
        DataValidator.isTextValid(data.get(FIRST_STREET_NAME));
        DataValidator.isNumberValid(data.get(FIRST_STREET_NUMBER));
        DataValidator.isNumberValid(data.get(FIRST_HOUSE_NUMBER));
    }
}
