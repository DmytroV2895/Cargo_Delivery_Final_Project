package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.entity.*;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.InvoiceDAO;
import com.varukha.webproject.model.dao.impl.InvoiceDAOImpl;
import com.varukha.webproject.model.service.InvoiceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static com.varukha.webproject.entity.Invoice.OrderStatus.ON_THE_WAY;
import static com.varukha.webproject.model.service.impl.ServiceTestUtil.getTestInvoice;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class InvoiceServiceImplTest {

    private static Invoice invoice;
    private static InvoiceDAO invoiceDAO;
    private static InvoiceService invoiceService;
    private static Map<String, String> invoiceData;
    private static Optional<Invoice> invoiceOptional;


    @BeforeAll
    public static void setUp() {
        invoiceDAO = mock(InvoiceDAOImpl.class);
        invoiceService = new InvoiceServiceImpl(invoiceDAO);
        invoiceData = new HashMap<>();

        invoiceData.put(ParameterAndAttribute.ORDER_PAYMENT_STATUS, String.valueOf(true));
        invoiceData.put(ParameterAndAttribute.ORDER_STATUS, String.valueOf(ON_THE_WAY));
        invoiceData.put(ParameterAndAttribute.DELIVERY_ID, "1");
        invoiceData.put(ParameterAndAttribute.USER_ID, "1");
        invoiceData.put(ParameterAndAttribute.ID_ORDER, "1");
        invoiceData.put(ParameterAndAttribute.FIRST_ADDRESS_ID, "1");
        invoiceData.put(ParameterAndAttribute.SECOND_ADDRESS_ID, "1");

        invoiceData.put(ParameterAndAttribute.ORDER_TYPE, "CARGO");
        invoiceData.put(ParameterAndAttribute.ORDER_PRICE, "500");
        invoiceData.put(ParameterAndAttribute.ORDER_WEIGHT, "500");
        invoiceData.put(ParameterAndAttribute.ORDER_LENGTH, "100");
        invoiceData.put(ParameterAndAttribute.ORDER_HEIGHT, "150");
        invoiceData.put(ParameterAndAttribute.ORDER_WIDTH, "50");
        invoiceData.put(ParameterAndAttribute.DELIVERY_DISTANCE, "1000");

        User user = new User.Builder()
                .setUserId(1L)
                .build();
        Order order = new Order.Builder()
                .setOrderId(1L)
                .build();
        Delivery delivery = new Delivery.Builder()
                .setDeliveryId(1L)
                .build();
        AddressFirst addressFirst = new AddressFirst.Builder()
                .setFirstAddressId(1L)
                .build();
        AddressSecond addressSecond = new AddressSecond.Builder()
                .setSecondAddressId(1L)
                .build();

        invoice = new Invoice.Builder()
                .setDeliveryPrice(BigDecimal.valueOf(600))
                .setDeliveryDate(Date.valueOf(LocalDate.now()))
                .setTotalPrice(BigDecimal.valueOf(1500))
                .setDeliveryPaymentStatus(true)
                .setOrderStatus(ON_THE_WAY)
                .setDelivery(delivery)
                .setUser(user)
                .setOrder(order)
                .setFirstAddress(addressFirst)
                .setSecondAddress(addressSecond)
                .build();
    }

    @Test
    void addInvoice() throws ServiceException, IncorrectInputException, DAOException {
        try(MockedStatic<InvoiceServiceImpl> mockedStatic = mockStatic(InvoiceServiceImpl.class)) {
            mockedStatic.when((MockedStatic.Verification) InvoiceServiceImpl.calculateDeliveryPrice(invoiceData)).thenReturn(BigDecimal.valueOf(600));
            mockedStatic.when((MockedStatic.Verification) InvoiceServiceImpl.calculateTotalPrice(invoiceData)).thenReturn(BigDecimal.valueOf(1500));
            when(invoiceDAO.addInvoice(isA(Invoice.class))).thenReturn(true);
            boolean expectedResult = invoiceService.addInvoice(invoiceData);
            assertTrue(expectedResult);
        }
    }

    @Test
    void updateInvoice() throws DAOException, ServiceException, IncorrectInputException, SQLException {

        try(MockedStatic<InvoiceServiceImpl> mockedStatic = mockStatic(InvoiceServiceImpl.class)) {
            mockedStatic.when((MockedStatic.Verification) InvoiceServiceImpl.calculateDeliveryPrice(invoiceData)).thenReturn(BigDecimal.valueOf(1000));
            mockedStatic.when((MockedStatic.Verification) InvoiceServiceImpl.calculateTotalPrice(invoiceData)).thenReturn(BigDecimal.valueOf(1500));
            when(invoiceDAO.updateInvoiceData(isA(Invoice.class))).thenReturn(true);
            boolean expectedResult = invoiceService.updateInvoice(invoiceData);
            assertTrue(expectedResult);
        }
    }

    @Test
    void updateDeliveryPaymentStatusByInvoiceId() {
    }

    @Test
    void getAllOrderInfoFromInvoiceByUserId() {
    }

    @Test
    void updateInvoiceDeliveryDateAndOrderStatus() {
    }

    @Test
    void getInvoiceById() throws DAOException, ServiceException {
        when(invoiceDAO.findInvoiceById(1L)).thenReturn(Optional.of(getTestInvoice()));
            assertEquals(Optional.of(getTestInvoice()), invoiceService.getInvoiceById(1L));
    }

    @Test
    void getInvoiceByDeliveryDate() throws DAOException, ServiceException {
        when(invoiceDAO.findInvoicesByDeliveryDate(String.valueOf(LocalDate.now()))).thenReturn(Optional.of(invoice));
        assertEquals(Optional.of(invoice), invoiceService.getInvoiceByDeliveryDate(String.valueOf(LocalDate.now())));
    }

    @Test
    void getInvoiceByDestinationDate() throws ServiceException, DAOException {
        when(invoiceDAO.findInvoiceByDestinationCity("SUMY", "ODESSA")).thenReturn(Optional.of(invoice));
        assertEquals(Optional.of(invoice), invoiceService.getInvoiceByDestinationCity("SUMY", "ODESSA"));
    }


    @Test
    void getNumberOfPages() {
    }

    @Test
    void getNumberOfPagesByUserId() {
    }

    @Test
    void getInvoicesFromRow() {
    }

    @Test
    void getAllOrdersInfoFromInvoiceByUserIdFromRow() {
    }

    @Test
    void getAllSortedOrdersInfoFromInvoiceByFirstAddressFromRow() {
    }




}