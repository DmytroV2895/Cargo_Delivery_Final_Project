package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import com.varukha.webproject.model.dao.AddressSecondDAO;

import com.varukha.webproject.model.service.AddressSecondService;
import com.varukha.webproject.model.service.validation.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.varukha.webproject.command.ParameterAndAttribute.*;

public class AddressSecondServiceImpl implements AddressSecondService {


    private static final Logger logger = LogManager.getLogger();
    private AddressSecondDAO addressSecondDAO;

    public AddressSecondServiceImpl(AddressSecondDAO addressSecondDAO) {
        this.addressSecondDAO = addressSecondDAO;
    }


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

    @Override
    public boolean updateAddressSecond(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add second address. AddressData" + data);
        boolean isChanged = false;
        isDataValid(data);
        AddressSecond addressSecond = new AddressSecond.Builder()
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
     * Validation input data
     * @param data for validation
     */
    private void isDataValid(Map<String, String> data) throws IncorrectInputException {
        DataValidator.isTextValid(data.get(SECOND_CITY));
        DataValidator.isTextValid(data.get(SECOND_STREET_NAME));
        DataValidator.isNumberValid(data.get(SECOND_STREET_NUMBER));
        DataValidator.isNumberValid(data.get(SECOND_HOUSE_NUMBER));

    }


}
