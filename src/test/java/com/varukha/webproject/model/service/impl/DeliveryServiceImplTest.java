package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.Delivery;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.DeliveryDAO;
import com.varukha.webproject.model.dao.impl.DeliveryDAOImpl;
import com.varukha.webproject.model.service.DeliveryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    private static DeliveryDAO deliveryDAO;
    private static DeliveryService deliveryService;
    private static Map<String, String> deliveryData;


    @BeforeAll
    public static void setUp() {
        deliveryDAO = mock(DeliveryDAOImpl.class);
        deliveryService = new DeliveryServiceImpl(deliveryDAO);
        deliveryData = new HashMap<>();

        deliveryData.put(ParameterAndAttribute.DELIVERY_ID, "1");
        deliveryData.put(ParameterAndAttribute.DELIVERY_TYPE, "BY_TRUCK");
        deliveryData.put(ParameterAndAttribute.DELIVERY_DISTANCE, "1000.0");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_NAME, "Oleksandr");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_SURNAME, "Pruvalov");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_PHONE, "+380668774412");
        deliveryData.put(ParameterAndAttribute.FIRST_CITY, "SUMY");
        deliveryData.put(ParameterAndAttribute.SECOND_CITY, "ODESSA");


    }


    @Test
    void testAddDelivery() throws ServiceException, DAOException, IncorrectInputException {
        long expectedResult = 1L;
        try(MockedStatic<DeliveryServiceImpl> mockedStatic = mockStatic(DeliveryServiceImpl.class)) {
            when(DeliveryServiceImpl.calculateDeliveryDistance(deliveryData)).thenReturn(1000.0);
            when(deliveryDAO.addDelivery(isA(Delivery.class))).thenReturn(1L);
            long actualResult = deliveryService.addDelivery(deliveryData);
            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    void testUpdateDelivery() throws DAOException, ServiceException, IncorrectInputException {
        boolean expectedResult = true;
        when(deliveryDAO.updateDeliveryData(isA(Delivery.class))).thenReturn(true);
        boolean actualResult = deliveryService.updateDelivery(deliveryData);
        assertEquals(expectedResult, actualResult);
    }
}