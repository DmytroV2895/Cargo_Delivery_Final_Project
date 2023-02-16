package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.service.AddressFirstService;
import com.varukha.webproject.model.service.validation.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.varukha.webproject.command.ParameterAndAttribute.*;






public class AddressFirstServiceImpl implements AddressFirstService {

    private static final Logger logger = LogManager.getLogger();
    private final AddressFirstDAO addressFirstDAO;

    public AddressFirstServiceImpl(AddressFirstDAO addressFirstDAO) {
        this.addressFirstDAO = addressFirstDAO;
    }



    @Override
    public long addAddressFirst(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add first address. AddressData" + data);
        long addressFirstId = 0;
        isDataValid(data);

        AddressFirst addressFirst = new AddressFirst.Builder()
                .setFirstCity(data.get(FIRST_CITY))
                .setFirstStreetName(data.get(FIRST_STREET_NAME))
                .setFirstStreetNumber(data.get(FIRST_STREET_NUMBER))
                .setFirstHouseNumber(data.get(FIRST_HOUSE_NUMBER))
                .build();
        try {
            addressFirstId = addressFirstDAO.addFirstAddress(addressFirst);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in addFirstAddress method: " + e);
            throw new ServiceException(e);
        }
        return addressFirstId;
    }

    @Override
    public boolean updateAddressFirst(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add first address. AddressData" + data);
        boolean isChanged = false;
        isDataValid(data);

        AddressFirst addressFirst = new AddressFirst.Builder()
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
     * Validation input data
     * @param data for validation
     */
    private void isDataValid(Map<String, String> data) throws IncorrectInputException {
        DataValidator.isTextValid(data.get(FIRST_CITY));
        DataValidator.isTextValid(data.get(FIRST_STREET_NAME));
        DataValidator.isNumberValid(data.get(FIRST_STREET_NUMBER));
        DataValidator.isNumberValid(data.get(FIRST_HOUSE_NUMBER));

    }
}
