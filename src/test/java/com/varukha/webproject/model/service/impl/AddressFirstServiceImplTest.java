package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.dao.impl.AddressFirstDAOImpl;
import com.varukha.webproject.model.service.AddressFirstService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressFirstServiceImplTest {


    private static AddressFirstDAO addressFirstDAO;
    private static AddressFirst addressFirst;
    private static AddressFirstService addressFirstService;
    private static Map<String, String> addressFirstData;


    @BeforeAll
    public static void setUp() {
        addressFirstDAO = mock(AddressFirstDAOImpl.class);
        addressFirstService = new AddressFirstServiceImpl(addressFirstDAO);
        addressFirstData = new HashMap<>();


        addressFirstData.put(ParameterAndAttribute.FIRST_CITY, "SUMY");
        addressFirstData.put(ParameterAndAttribute.FIRST_STREET_NAME, "Mira");
        addressFirstData.put(ParameterAndAttribute.FIRST_STREET_NUMBER, "25");
        addressFirstData.put(ParameterAndAttribute.FIRST_HOUSE_NUMBER, "56");
        addressFirstData.put(ParameterAndAttribute.FIRST_ADDRESS_ID, "1");

        addressFirst = new AddressFirst.Builder()
                .setFirstCity("SUMY")
                .setFirstStreetName("Mira")
                .setFirstStreetNumber("25")
                .setFirstHouseNumber("56")
                .build();

    }

    @Test
    void testAddAddressFirst() throws ServiceException, DAOException, IncorrectInputException {
        addressFirstService.addAddressFirst(addressFirstData);
        Mockito.verify(addressFirstDAO, Mockito.times(1)).addFirstAddress(addressFirst);
    }

    @Test
    void testUpdateAddAddressFirst() throws DAOException, ServiceException, IncorrectInputException {
        boolean expectedResult = true;
        when(addressFirstDAO.updateFirstAddressData(isA(AddressFirst.class))).thenReturn(true);
        boolean actualResult = addressFirstService.updateAddressFirst(addressFirstData);
        assertEquals(expectedResult, actualResult);
    }

}