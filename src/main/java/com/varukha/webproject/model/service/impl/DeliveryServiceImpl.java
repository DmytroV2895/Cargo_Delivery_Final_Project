package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.model.entity.Delivery;
import com.varukha.webproject.model.entity.Delivery.DeliveryType;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.DeliveryDAO;
import com.varukha.webproject.model.service.DeliveryService;
import com.varukha.webproject.util.validation.DataValidator;
import com.varukha.webproject.util.calculator.util.DistanceCalculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class DeliveryServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger logger = LogManager.getLogger();
    private final DeliveryDAO deliveryDAO;

    public DeliveryServiceImpl(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

    /**
     * Method addDelivery used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new delivery data to database.
     *
     * @param data contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public long addDelivery(Map<String, String> data) throws ServiceException {
        logger.log(Level.DEBUG, "Add delivery. Data" + data);
        long deliveryId = 0;
        isDataValid(data);
        Delivery delivery = new Delivery.Builder()
                .setDeliveryType(DeliveryType.valueOf(String.valueOf(data.get(DELIVERY_TYPE))))
                .setDeliveryDistance(calculateDeliveryDistance(data))
                .setRecipientName(data.get(RECIPIENT_NAME))
                .setRecipientSurname(data.get(RECIPIENT_SURNAME))
                .setRecipientPhone(data.get(RECIPIENT_PHONE))
                .build();
        try {
            deliveryId = deliveryDAO.addDelivery(delivery);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method addDelivery: " + e);
            throw new ServiceException(e);
        }
        return deliveryId;
    }

    /**
     * Method updateDelivery is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update delivery data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean updateDelivery(Map<String, String> data) throws ServiceException {
        logger.log(Level.DEBUG, "Update delivery. Data" + data);
        boolean isChanged = false;
        isDataValid(data);
        Delivery delivery = new Delivery.Builder()
                .setDeliveryId(Long.parseLong(data.get(DELIVERY_ID)))
                .setDeliveryType(DeliveryType.valueOf(String.valueOf(data.get(DELIVERY_TYPE))))
                .setDeliveryDistance(calculateDeliveryDistance(data))
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
     * Method isDataValid used to validation input data
     *
     * @param data for validation from request
     */
    private void isDataValid(Map<String, String> data) {
        DataValidator.isNameValid(data.get(RECIPIENT_NAME));
        DataValidator.isNameValid(data.get(RECIPIENT_SURNAME));
        DataValidator.isPhoneNumberValid(data.get(RECIPIENT_PHONE));
    }

    /**
     * Method calculateDeliveryDistance used to calculate distance between two cities
     *
     * @param calculationData data from order creation page
     * @return distance between two cities in km
     */
    static double calculateDeliveryDistance(Map<String, String> calculationData) {
        double deliveryDistance = 0;
        String firstCity = calculationData.get(FIRST_CITY);
        String secondCity = calculationData.get(SECOND_CITY);
        if (firstCity.equals(secondCity)) {
            logger.log(Level.DEBUG, "Distance between first city: " + firstCity +
                    " and second city: " + secondCity + " is " + deliveryDistance);
            return deliveryDistance;
        } else {
            return DistanceCalculator.calculateDistanceBetweenTwoCities(firstCity, secondCity);
        }
    }
}


