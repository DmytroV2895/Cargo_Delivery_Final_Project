package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.Order;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.OrderDAO;
import com.varukha.webproject.model.dao.impl.OrderDAOImpl;
import com.varukha.webproject.model.service.OrderService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private static OrderDAO orderDAO;
    private static Order order;
    private static OrderService orderService;
    private static Map<String, String> orderData;


    @BeforeAll
    public static void setUp() {
        orderDAO = mock(OrderDAOImpl.class);
        orderService = new OrderServiceImpl(orderDAO);
        orderData = new HashMap<>();

        orderData.put(ParameterAndAttribute.ID_ORDER, "1");
        orderData.put(ParameterAndAttribute.ORDER_NAME, "Computer");
        orderData.put(ParameterAndAttribute.ORDER_TYPE, "CARGO");
        orderData.put(ParameterAndAttribute.ORDER_DESCRIPTION, "New computer");
        orderData.put(ParameterAndAttribute.ORDER_PRICE, "15000");
        orderData.put(ParameterAndAttribute.ORDER_WEIGHT, "50");
        orderData.put(ParameterAndAttribute.ORDER_LENGTH, "34");
        orderData.put(ParameterAndAttribute.ORDER_HEIGHT, "67");
        orderData.put(ParameterAndAttribute.ORDER_WIDTH, "45");
        orderData.put(ParameterAndAttribute.ORDER_VOLUME, "50");
        orderData.put(ParameterAndAttribute.ORDER_VOLUME_WEIGHT, "50");

        order = new Order.Builder()
                .setOrderName("Computer")
                .setOrderType(Order.Type.valueOf("CARGO"))
                .setOrderDescription("New computer")
                .setOrderPrice(BigDecimal.valueOf(15000))
                .setOrderWeight(50)
                .setOrderLength(34)
                .setOrderHeight(67)
                .setOrderWidth(45)
                .setOrderVolume(1025.1)
                .setOrderVolumeWeight(50)
                .build();
    }

    @Test
    void testAddOrder() throws DAOException, ServiceException, IncorrectInputException {
       orderService.addOrder(orderData);
        Mockito.verify(orderDAO, Mockito.times(1)).addOrder(order);
    }

    @Test
    void testUpdateAddOrder() throws DAOException, ServiceException, IncorrectInputException {
        boolean expectedResult = true;
        when(orderDAO.updateOrderData(isA(Order.class))).thenReturn(true);
        boolean actualResult = orderService.updateOrder(orderData);
        assertEquals(expectedResult, actualResult);
    }
}