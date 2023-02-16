package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.entity.Delivery.DeliveryType;

import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;

import com.varukha.webproject.model.dao.DeliveryDAO;

import com.varukha.webproject.model.service.DeliveryService;
import com.varukha.webproject.model.service.validation.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.varukha.webproject.command.ParameterAndAttribute.*;


/**
 * Class Delivery service
 *
 * @author Dmytro Varukha
 */

public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger logger = LogManager.getLogger();
    private final DeliveryDAO deliveryDAO;

    public DeliveryServiceImpl(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }


    @Override
    public long addDelivery(Map<String, String> deliveryData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add delivery. Data" + deliveryData);
        long deliveryId = 0;
        isDataValid(deliveryData);
        Delivery delivery = new Delivery.Builder()
                .setDeliveryType(DeliveryType.valueOf(String.valueOf(deliveryData.get(DELIVERY_TYPE))))
                .setDeliveryDistance(Integer.parseInt(deliveryData.get(DELIVERY_DISTANCE)))
                .setRecipientName(deliveryData.get(RECIPIENT_NAME))
                .setRecipientSurname(deliveryData.get(RECIPIENT_SURNAME))
                .setRecipientPhone(deliveryData.get(RECIPIENT_PHONE))
                .build();
        try {
            deliveryId = deliveryDAO.addDelivery(delivery);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method addDelivery: " + e);
            throw new ServiceException(e);
        }
        return deliveryId;
    }


    @Override
    public boolean updateDelivery(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Update delivery. Data" + data);
        boolean isChanged = false;
        isDataValid(data);
        Delivery delivery = new Delivery.Builder()
                .setDeliveryType(DeliveryType.valueOf(String.valueOf(data.get(DELIVERY_TYPE))))
                .setDeliveryDistance(Integer.parseInt(data.get(DELIVERY_DISTANCE)))
                .setRecipientName(data.get(RECIPIENT_NAME))
                .setRecipientSurname(data.get(RECIPIENT_SURNAME))
                .setRecipientPhone(data.get(RECIPIENT_PHONE))
                .build();
        try {
            isChanged = deliveryDAO.updateDeliveryData(delivery);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in updateDeliveryData method: " + e);
            throw new ServiceException(e);
        }
        return isChanged;
    }


    /**
     * Validation input data
     * @param data for validation
     * @return boolean true if data valid
     */
    private void isDataValid(Map<String, String> data) throws IncorrectInputException {
        DataValidator.isAllNumbersValid(data.get(DELIVERY_DISTANCE));
        DataValidator.isNameValid(data.get(RECIPIENT_NAME));
        DataValidator.isNameValid(data.get(RECIPIENT_SURNAME));
        DataValidator.isPhoneNumberValid(data.get(RECIPIENT_PHONE));

    }
}


