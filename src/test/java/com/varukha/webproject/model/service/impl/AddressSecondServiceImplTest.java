package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.entity.AddressFirst;
import com.varukha.webproject.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.AddressSecondDAO;
import com.varukha.webproject.model.dao.impl.AddressSecondDAOImpl;
import com.varukha.webproject.model.service.AddressSecondService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import static com.varukha.webproject.command.ParameterAndAttribute.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressSecondServiceImplTest {

    private static AddressSecondDAO addressSecondDAO;
    private static AddressSecond addressSecond;
    private static AddressSecondService addressSecondService;
    private static Map<String, String> addressSecondData;



    @BeforeAll
    public static void setUp() {
        addressSecondDAO = mock(AddressSecondDAOImpl.class);
        addressSecondService = new AddressSecondServiceImpl(addressSecondDAO);
        addressSecondData = new HashMap<>();


        addressSecondData.put(ParameterAndAttribute.SECOND_CITY, "CHARKIV");
        addressSecondData.put(ParameterAndAttribute.SECOND_STREET_NAME, "Lisna");
        addressSecondData.put(ParameterAndAttribute.SECOND_STREET_NUMBER, "35");
        addressSecondData.put(ParameterAndAttribute.SECOND_HOUSE_NUMBER, "85");
        addressSecondData.put(USER_ID, "1");

        addressSecond = new AddressSecond.Builder()
                .setSecondCity("CHARKIV")
                .setSecondStreetName("Lisna")
                .setSecondStreetNumber("35")
                .setSecondHouseNumber("85")
                .build();

    }

    @Test
    void addAddressSecond() throws DAOException, ServiceException, IncorrectInputException {
        addressSecondService.addAddressSecond(addressSecondData);
        Mockito.verify(addressSecondDAO, Mockito.times(1)).addSecondAddress(addressSecond);
    }

    @Test
    void testUpdateAddAddressSecond() throws DAOException, ServiceException, IncorrectInputException {
        boolean expectedResult = true;
        when(addressSecondDAO.updateSecondAddressData(isA(AddressSecond.class))).thenReturn(true);
        boolean actualResult = addressSecondService.updateAddressSecond(addressSecondData);
        assertEquals(expectedResult, actualResult);
    }

}