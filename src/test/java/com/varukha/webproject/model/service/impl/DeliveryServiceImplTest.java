package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.entity.AddressSecond;
import com.varukha.webproject.entity.Delivery;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.DeliveryDAO;
import com.varukha.webproject.model.dao.impl.DeliveryDAOImpl;
import com.varukha.webproject.model.service.DeliveryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeliveryServiceImplTest {

    private static DeliveryDAO deliveryDAO;
    private static Delivery delivery;
    private static DeliveryService deliveryService;
    private static Map<String, String> deliveryData;


    @BeforeAll
    public static void setUp() {
        deliveryDAO = mock(DeliveryDAOImpl.class);
        deliveryService = new DeliveryServiceImpl(deliveryDAO);
        deliveryData = new HashMap<>();

        deliveryData.put(ParameterAndAttribute.DELIVERY_TYPE, "BY_TRUCK");
        deliveryData.put(ParameterAndAttribute.DELIVERY_DISTANCE, "1000");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_NAME, "Oleksandr");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_SURNAME, "Pruvalov");
        deliveryData.put(ParameterAndAttribute.RECIPIENT_PHONE, "+380668774412");

        delivery = new Delivery.Builder()
                .setDeliveryType(Delivery.DeliveryType.BY_TRUCK)
                .setDeliveryDistance(1000)
                .setRecipientName("Oleksandr")
                .setRecipientSurname("Pruvalov")
                .setRecipientPhone("+380668774412")
                .build();
    }


    @Test
    void testAddDelivery() throws ServiceException, DAOException, IncorrectInputException {
        deliveryService.addDelivery(deliveryData);
        Mockito.verify(deliveryDAO, Mockito.times(1)).addDelivery(delivery);
    }

    @Test
    void testUpdateDelivery() throws DAOException, ServiceException, IncorrectInputException {
        boolean expectedResult = true;
        when(deliveryDAO.updateDeliveryData(isA(Delivery.class))).thenReturn(true);
        boolean actualResult = deliveryService.updateDelivery(deliveryData);
        assertEquals(expectedResult, actualResult);
    }
}