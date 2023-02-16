package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.entity.Order;
import com.varukha.webproject.entity.Order.Type;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.OrderDAO;
import com.varukha.webproject.model.service.OrderService;

import com.varukha.webproject.model.service.validation.DataValidator;
import com.varukha.webproject.util.Calculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

import static com.varukha.webproject.command.ParameterAndAttribute.*;


/**
 * Class Order service
 *
 * @author Dmytro Varukha
 */

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    @Override
    public long addOrder(Map<String, String> orderData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Add order. OrderData" + orderData);
        long orderId = 0;
        isDataValid(orderData);
            Order order = new Order.Builder()
                    .setOrderName(orderData.get(ORDER_NAME))
                    .setOrderType(Type.valueOf(orderData.get(ORDER_TYPE)))
                    .setOrderDescription(orderData.get(ORDER_DESCRIPTION))
                    .setOrderPrice(new BigDecimal(orderData.get(ORDER_PRICE)))
                    .setOrderWeight(Double.parseDouble(orderData.get(ORDER_WEIGHT)))
                    .setOrderLength(Double.parseDouble(orderData.get(ORDER_LENGTH)))
                    .setOrderHeight(Double.parseDouble(orderData.get(ORDER_HEIGHT)))
                    .setOrderWidth(Double.parseDouble(orderData.get(ORDER_WIDTH)))
                    .setOrderVolume(calculateOrderVolume(orderData))
                    .setOrderVolumeWeight(calculateOrderVolumeWeight(orderData))
                    .setUserId(createUser(orderData.get(USER_ID)))
                    .setDeliveryId(createDelivery(orderData.get(DELIVERY_ID)))
                    .build();

            try {
                orderId = orderDAO.addOrder(order);
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in method addOrder: " + e);
                throw new ServiceException(e);
            }
            return orderId;
        }

        @Override
        public boolean updateOrder(Map < String, String > data) throws ServiceException, IncorrectInputException {
            logger.log(Level.DEBUG, "Update order data" + data);
            boolean isChanged = false;
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
                isChanged = orderDAO.updateOrderData(order);
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in updateOrderData method: " + e);
                throw new ServiceException(e);
            }
            return isChanged;

        }

        /**
         * Calculation order volume
         * @param calculationData from order creation page
         * @return order volume
         */
        private double calculateOrderVolume (Map <String, String> calculationData) throws IncorrectInputException {
            String orderLength = calculationData.get(ORDER_LENGTH);
            String orderHeight = calculationData.get(ORDER_HEIGHT);
            String orderWidth = calculationData.get(ORDER_WIDTH);

            double orderLengthToDouble = Double.parseDouble(orderLength);
            double orderHeightToDouble = Double.parseDouble(orderHeight);
            double orderWidthToDouble = Double.parseDouble(orderWidth);

            return Calculator.calculateOrderVolume(orderLengthToDouble,
                    orderHeightToDouble,
                    orderWidthToDouble);

        }

        /**
         * Calculation order volume weight
         * @param calculationData from order creation page
         * @return order volume weight
         */
        private double calculateOrderVolumeWeight (Map < String, String > calculationData) throws IncorrectInputException {
            String orderLength = calculationData.get(ORDER_LENGTH);
            String orderHeight = calculationData.get(ORDER_HEIGHT);
            String orderWidth = calculationData.get(ORDER_WIDTH);
            String orderWeight = calculationData.get(ORDER_WEIGHT);

            double orderLengthToDouble = Double.parseDouble(orderLength);
            double orderHeightToDouble = Double.parseDouble(orderHeight);
            double orderWidthToDouble = Double.parseDouble(orderWidth);
            double orderWeightToDouble = Double.parseDouble(orderWeight);

            return Calculator.calculateOrderVolumeWeight(orderLengthToDouble,
                    orderHeightToDouble,
                    orderWidthToDouble,
                    orderWeightToDouble);
        }

        /**
         * Set delivery id
         * @param id
         * @return new Order, that has only id
         */
        private Delivery createDelivery (String id){
            long deliveryId = Long.parseLong(id);
            Delivery delivery = new Delivery.Builder()
                    .setDeliveryId(deliveryId)
                    .build();
            return delivery;
        }

        /**
         * Set user id
         * @param id
         * @return new User, that has only id
         */
        private User createUser (String id){
            long userId = Long.parseLong(id);
            User user = new User.Builder()
                    .setUserId(userId)
                    .build();
            return user;
        }

        /**
         * Validation input data
         * @param data for validation
         */
        private void isDataValid (Map <String, String> data) throws IncorrectInputException {
            DataValidator.isAllNumbersValid(data.get(ORDER_PRICE));
            DataValidator.isAllNumbersValid(data.get(ORDER_WEIGHT));
            DataValidator.isAllNumbersValid(data.get(ORDER_LENGTH));
            DataValidator.isAllNumbersValid(data.get(ORDER_HEIGHT));
            DataValidator.isAllNumbersValid(data.get(ORDER_WIDTH));

        }


}