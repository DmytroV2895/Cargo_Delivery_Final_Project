package com.varukha.webproject.controller.context;


import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.*;
import com.varukha.webproject.model.service.*;
import com.varukha.webproject.model.service.impl.*;



/**
 * Class AppContext  contains all required to correct application work objects.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public class AppContext {

    private static AppContext appContext;
    private final UserService userService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final DeliveryService deliveryService;
    private final AddressFirstService addressFirstService;
    private final AddressSecondService addressSecondService;
    private final  ConnectionPool connectionPool;

    public UserService getUserService() {
        return userService;
    }
    public OrderService getOrderService() {
        return orderService;
    }
    public InvoiceService getInvoiceService() {
        return invoiceService;
    }
    public DeliveryService getDeliveryService() {
        return deliveryService;
    }
    public AddressFirstService getAddressFirstService() {
        return addressFirstService;
    }
    public AddressSecondService getAddressSecondService() {
        return addressSecondService;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
    private AppContext() {
        connectionPool = ConnectionPool.getInstance();
        userService = new UserServiceImpl(new UserDAOImpl(ConnectionPool.getInstance()));
        invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl(ConnectionPool.getInstance()));
        deliveryService = new DeliveryServiceImpl(new DeliveryDAOImpl(ConnectionPool.getInstance()));
        addressFirstService = new AddressFirstServiceImpl(new AddressFirstDAOImpl(ConnectionPool.getInstance()));
        addressSecondService = new AddressSecondServiceImpl(new AddressSecondDAOImpl(ConnectionPool.getInstance()));
        orderService = new OrderServiceImpl(new OrderDAOImpl(ConnectionPool.getInstance()));
    }

    /**
     * Method getAppContext used to get instance of AppContext to use in commands.
     * @return instance of AppContext
     */
    public static AppContext getAppContext() {
        return appContext;
    }

    /**
     * Method createAppContext used to create instance of AppContext to use in commands.
     */
    public static void createAppContext() {
        appContext = new AppContext();
    }

}