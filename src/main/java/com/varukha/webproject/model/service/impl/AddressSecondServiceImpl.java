package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.model.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.AddressSecondDAO;
import com.varukha.webproject.model.service.AddressSecondService;
import com.varukha.webproject.util.validation.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class AddressSecondServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class AddressSecondServiceImpl implements AddressSecondService {

    private static final Logger logger = LogManager.getLogger();
    private final AddressSecondDAO addressSecondDAO;

    public AddressSecondServiceImpl(AddressSecondDAO addressSecondDAO) {
        this.addressSecondDAO = addressSecondDAO;
    }

    /**
     * Method addAddressSecond used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new second address to database.
     *
     * @param addressData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException        is wrapper for DAOException that throws exception during the runtime because of
     *                                 data validation fail or others mistakes.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    @Override
    public long addAddressSecond(Map<String, String> addressData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add second address. AddressData" + addressData);
        long addressSecondId = 0;
        isDataValid(addressData);
        AddressSecond addressSecond = new AddressSecond.Builder()
                .setSecondCity(addressData.get(SECOND_CITY))
                .setSecondStreetName(addressData.get(SECOND_STREET_NAME))
                .setSecondStreetNumber(addressData.get(SECOND_STREET_NUMBER))
                .setSecondHouseNumber(addressData.get(SECOND_HOUSE_NUMBER))
                .build();
        try {
            addressSecondId = addressSecondDAO.addSecondAddress(addressSecond);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method addSecondAddress: " + e);
            throw new ServiceException(e);
        }
        return addressSecondId;
    }

    /**
     * Method updateAddressSecond is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update second address data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException        is wrapper for DAOException that throws exception during the runtime because of
     *                                 data validation fail or others mistakes.
     * @throws IncorrectInputException is an exception that throws during the runtime because of incorrect data input.
     */
    @Override
    public boolean updateAddressSecond(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add second address. AddressData" + data);
        boolean isChanged = false;
        isDataValid(data);
        AddressSecond addressSecond = new AddressSecond.Builder()
                .setSecondAddressId(Long.parseLong(data.get(SECOND_ADDRESS_ID)))
                .setSecondCity(data.get(SECOND_CITY))
                .setSecondStreetName(data.get(SECOND_STREET_NAME))
                .setSecondStreetNumber(data.get(SECOND_STREET_NUMBER))
                .setSecondHouseNumber(data.get(SECOND_HOUSE_NUMBER))
                .build();
        try {
            isChanged = addressSecondDAO.updateSecondAddressData(addressSecond);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method updateAddressSecond: " + e);
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
        DataValidator.isTextValid(data.get(SECOND_CITY));
        DataValidator.isTextValid(data.get(SECOND_STREET_NAME));
        DataValidator.isNumberValid(data.get(SECOND_STREET_NUMBER));
        DataValidator.isNumberValid(data.get(SECOND_HOUSE_NUMBER));

    }
}
