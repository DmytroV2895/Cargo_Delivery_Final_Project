package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.model.entity.Order;
import com.varukha.webproject.model.entity.Order.Type;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.OrderDAO;
import com.varukha.webproject.model.service.OrderService;
import com.varukha.webproject.util.validation.DataValidator;
import com.varukha.webproject.util.calculator.util.CalculationUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.util.Map;
import static com.varukha.webproject.command.ParameterAndAttribute.*;

/**
 * Class OrderServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Method addOrder used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new order data to database.
     *
     * @param data contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public long addOrder(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add order. OrderData" + data);
        long orderId = 0;
        isDataValid(data);
        Order order = new Order.Builder()
                .setOrderName(data.get(ORDER_NAME))
                .setOrderType(Type.valueOf(data.get(ORDER_TYPE)))
                .setOrderDescription(data.get(ORDER_DESCRIPTION))
                .setOrderPrice(new BigDecimal(data.get(ORDER_PRICE)))
                .setOrderWeight(Double.parseDouble(data.get(ORDER_WEIGHT)))
                .setOrderLength(Double.parseDouble(data.get(ORDER_LENGTH)))
                .setOrderHeight(Double.parseDouble(data.get(ORDER_HEIGHT)))
                .setOrderWidth(Double.parseDouble(data.get(ORDER_WIDTH)))
                .setOrderVolume(calculateOrderVolume(data))
                .setOrderVolumeWeight(calculateOrderVolumeWeight(data))
                .build();
        try {
            orderId = orderDAO.addOrder(order);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method addOrder: " + e);
            throw new ServiceException(e);
        }
        return orderId;
    }

    /**
     * Method updateOrder is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to update order data.
     *
     * @param data contain a set of data to update from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new invoice data was set successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean updateOrder(Map<String, String> data) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Update order data" + data);
        boolean isChanged = false;
        isDataValid(data);
        Order order = new Order.Builder()
                .setOrderId(Long.parseLong(data.get(ID_ORDER)))
                .setOrderName(data.get(ORDER_NAME))
                .setOrderType(Type.valueOf(data.get(ORDER_TYPE)))
                .setOrderDescription(data.get(ORDER_DESCRIPTION))
                .setOrderPrice(new BigDecimal(data.get(ORDER_PRICE)))
                .setOrderWeight(Double.parseDouble(data.get(ORDER_WEIGHT)))
                .setOrderLength(Double.parseDouble(data.get(ORDER_LENGTH)))
                .setOrderHeight(Double.parseDouble(data.get(ORDER_HEIGHT)))
                .setOrderWidth(Double.parseDouble(data.get(ORDER_WIDTH)))
                .setOrderVolume(calculateOrderVolume(data))
                .setOrderVolumeWeight(calculateOrderVolumeWeight(data))
                .build();
        try {
            isChanged = orderDAO.updateOrderData(order);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in updateOrder method: " + e);
            throw new ServiceException(e);
        }
        return isChanged;

    }

    /**
     * Method calculateOrderVolume used to calculate order volume
     *
     * @param calculationData data from user request
     * @return order volume
     */
    private double calculateOrderVolume(Map<String, String> calculationData) {
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);

        double orderLengthToDouble = Double.parseDouble(orderLength);
        double orderHeightToDouble = Double.parseDouble(orderHeight);
        double orderWidthToDouble = Double.parseDouble(orderWidth);

        return CalculationUtil.calculateOrderVolume(orderLengthToDouble,
                orderHeightToDouble,
                orderWidthToDouble);
    }

    /**
     * Method calculateOrderVolumeWeight used to calculate order volume weight
     *
     * @param calculationData data from user request
     * @return order volume weight
     */
    private double calculateOrderVolumeWeight(Map<String, String> calculationData) {
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);
        String orderWeight = calculationData.get(ORDER_WEIGHT);

        double orderLengthToDouble = Double.parseDouble(orderLength);
        double orderHeightToDouble = Double.parseDouble(orderHeight);
        double orderWidthToDouble = Double.parseDouble(orderWidth);
        double orderWeightToDouble = Double.parseDouble(orderWeight);

        return CalculationUtil.calculateOrderVolumeWeight(orderLengthToDouble,
                orderHeightToDouble,
                orderWidthToDouble,
                orderWeightToDouble);
    }

    /**
     * Method isDataValid used to validation input data
     *
     * @param data for validation from request
     */
    private void isDataValid(Map<String, String> data) {
        DataValidator.isAllNumbersValid(data.get(ORDER_PRICE));
        DataValidator.isAllNumbersValid(data.get(ORDER_WEIGHT));
        DataValidator.isAllNumbersValid(data.get(ORDER_LENGTH));
        DataValidator.isAllNumbersValid(data.get(ORDER_HEIGHT));
        DataValidator.isAllNumbersValid(data.get(ORDER_WIDTH));

    }

}