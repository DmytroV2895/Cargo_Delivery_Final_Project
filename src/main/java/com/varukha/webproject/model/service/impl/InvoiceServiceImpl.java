package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.entity.*;
import com.varukha.webproject.entity.Invoice.OrderStatus;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.IncorrectInputException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.InvoiceDAO;
import com.varukha.webproject.model.service.InvoiceService;
import com.varukha.webproject.model.service.validation.DataValidator;
import com.varukha.webproject.util.Calculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.varukha.webproject.command.ParameterAndAttribute.*;


/**
 * Class Invoice Service
 * @author Dmytro Varukha
 */

public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LogManager.getLogger();
    private final InvoiceDAO invoiceDAO;
    private final int numberOfInvoicesOnPage = 3;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public boolean addInvoice(Map<String, String> invoiceData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Save invoice to database. Invoice data" + invoiceData);
        boolean isAdded;
        if (isInvoiceDataValid(invoiceData)) {

            User userId = createUser(invoiceData.get(USER_ID));
            Order orderId = createOrder(invoiceData.get(ID_ORDER));
            Delivery deliveryId = createDelivery(invoiceData.get(DELIVERY_ID));
            AddressFirst addressFirst = createFirstAddress(invoiceData.get(FIRST_ADDRESS_ID));
            AddressSecond addressSecond = createSecondAddress(invoiceData.get(SECOND_ADDRESS_ID));

            Invoice invoice = new Invoice.Builder()
                    .setUser(userId)
                    .setOrder(orderId)
                    .setDelivery(deliveryId)
                    .setDeliveryPrice(calculateDeliveryPrice(invoiceData))
                    .setTotalPrice(calculateTotalPrice(invoiceData))
                    .setDeliveryPaymentStatus(false)
                    .setOrderStatus(OrderStatus.NOT_PROCESSED)
                    .setFirstAddress(addressFirst)
                    .setSecondAddress(addressSecond)
                    .build();
            try {
                isAdded = invoiceDAO.addInvoice(invoice);
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in addInvoice method: " + e);
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.DEBUG, "Incorrect data input" + invoiceData);
            throw new IncorrectInputException("Incorrect data input" + invoiceData);
        }
        return isAdded;
    }

    @Override
    public boolean updateInvoice(Map<String, String> invoiceData) throws ServiceException, IncorrectInputException {
        logger.log(Level.DEBUG, "Update invoice in database. Invoice invoiceData" + invoiceData);
        boolean isChanged = false;
        if (isInvoiceDataValid(invoiceData)) {
            Invoice invoice = new Invoice.Builder()
                    .setDeliveryPrice(calculateDeliveryPrice(invoiceData))
                    .setTotalPrice(calculateTotalPrice(invoiceData))
                    .build();
            try {
                isChanged = invoiceDAO.updateInvoiceData(invoice);
                logger.log(Level.DEBUG, "Updated invoice invoiceData" + invoiceData);
            } catch (DAOException | SQLException e) {
                logger.log(Level.ERROR, "DAO exception in updateInvoiceData method: " + e);
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.DEBUG, "Incorrect data input" + invoiceData);
            throw new IncorrectInputException("Incorrect data input" + invoiceData);
        }
        return isChanged;
    }


//    @Override
//    public Optional<Invoice> getInvoiceByRecipientPhone(String recipientPhone) throws ServiceException {
//        logger.log(Level.DEBUG, "Get invoice by invoiceId:" + recipientPhone);
//        Optional<Invoice> optionalInvoice;
//        try {
//            if (recipientPhone == null) {
//                optionalInvoice = Optional.empty();
//                throw new ServiceException("No invoices was found by recipient phone " + recipientPhone);
//            } else {
//                optionalInvoice = invoiceDAO.findInvoiceByRecipientPhone(recipientPhone);
//            }
//        } catch (DAOException e) {
//            logger.log(Level.ERROR, "DAO exception in findInvoiceByRecipientPhone method " + e);
//            throw new ServiceException(e);
//        }
//        return optionalInvoice;
//    }


    @Override
    public boolean updateDeliveryPaymentStatusByInvoiceId(long invoiceId) throws ServiceException {
        logger.log(Level.DEBUG, "Update delivery payment status by invoiceId: " + invoiceId);
        boolean isUpdated;
        try {
            isUpdated = invoiceDAO.updateDeliveryPaymentStatusByInvoiceId(invoiceId);
        } catch (DAOException e) {
            logger.log(Level.ERROR,
                    "DAO exception in method updateDeliveryPaymentStatusByInvoiceId, when we try update payment status by invoiceId :" + invoiceId + ". " + e);
            throw new ServiceException(e);
        }
        return isUpdated;
    }


    @Override
    public boolean updateInvoiceDeliveryDateAndOrderStatus(Map<String, String> invoiceData) throws ServiceException {
        logger.log(Level.INFO, "Update invoice delivery invoiceData and order status by manager " + invoiceData);
        boolean isUpdated = false;
        try {
            Date deliveryDate = Date.valueOf(invoiceData.get(DELIVERY_DATE));
            OrderStatus orderStatus = OrderStatus.valueOf(invoiceData.get(ORDER_STATUS));

            Invoice invoice = new Invoice.Builder()
                    .setInvoiceId(Long.parseLong(invoiceData.get(ID_INVOICE)))
                    .setDeliveryDate(deliveryDate)
                    .setOrderStatus(orderStatus)
                    .build();

            isUpdated = invoiceDAO.changeInvoiceDeliveryDateAndOrderStatus(invoice);
        } catch (DAOException e) {
            logger.log(Level.ERROR,
                    "DAO exception in updateInvoiceDeliveryDateAndOrderStatus method: " + invoiceData + ". " + e);
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    @Override
    public Optional<Invoice> getInvoiceById(long invoiceId) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoice by Id:" + invoiceId);
        Optional<Invoice> invoice;
        try {
            invoice = invoiceDAO.findInvoiceById(invoiceId);
            logger.log(Level.DEBUG, "Invoice was found" + invoice);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceById method.  InvoiceId: " + invoiceId);
            throw new ServiceException(e);
        }
        return invoice;
    }


    @Override
    public Optional<Invoice> getInvoiceByDeliveryDate(String deliveryDate) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoice by deliveryDate:" + deliveryDate);
        Optional<Invoice> optionalInvoice;
        try {
            optionalInvoice = invoiceDAO.findInvoicesByDeliveryDate(deliveryDate);
            logger.log(Level.DEBUG, "Invoice was found. " + optionalInvoice + " Delivery date " + deliveryDate);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceByDeliveryDate method.  Delivery date " + deliveryDate);
            throw new ServiceException(e);
        }
        return optionalInvoice;
    }

    @Override
    public Optional<Invoice> getInvoiceByDestinationCity(String firstCity, String secondCity) throws ServiceException {
        logger.log(Level.INFO, "Get invoice by destination. First city: " + firstCity + " Second city: " + secondCity);
        Optional<Invoice> invoiceOptional;
        try {
            invoiceOptional = invoiceDAO.findInvoiceByDestinationCity(firstCity, secondCity);
            logger.log(Level.DEBUG, "Invoice was found. " + invoiceOptional + " First city: " + firstCity + " Second city: " + secondCity);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getInvoiceByDestinationDate method.  First city: " + firstCity + " Second city: " + secondCity);
            throw new ServiceException(e);
        }
        return invoiceOptional;
    }

    @Override
    public int getNumberOfPages() throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages:");
        int numberOfPages;
        int numberOfInvoices;
        try {
            numberOfInvoices = invoiceDAO.findNumberOfRowsInInvoiceTable();
            if (numberOfInvoices > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfInvoices / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findNumberOfRowsInInvoiceTable method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

    @Override
    public int getNumberOfRowsInInvoiceTableByUserId(long userId) throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages by userId:");
        int numberOfPages;
        int numberOfInvoices;
        try {
            numberOfInvoices = invoiceDAO.findNumberOfRowsInInvoiceTableByUserId(userId);
            if (numberOfInvoices > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfInvoices / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findNumberOfRowsInInvoiceTable method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

    @Override
    public List<Invoice> getInvoicesFromRow(int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get invoices from row of invoice table. Page number: " + pageNumber);
        List<Invoice> invoices;
        int invoiceFromRow;
        if (pageNumber > 1) {
            invoiceFromRow = (pageNumber - 1) * numberOfInvoicesOnPage;
        } else {
            invoiceFromRow = 0;
        }
        try {
            invoices = invoiceDAO.findAllInvoicesFromRow(invoiceFromRow, numberOfInvoicesOnPage);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findAllInvoicesFromRow method " + e);
            throw new ServiceException(e);
        }
        return invoices;
    }

    @Override
    public List<Invoice> getAllOrdersInfoFromInvoiceByUserId(long userId, int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get all order info from invoice by userId:" + userId);
        List<Invoice> invoiceList;
        int invoiceFromRow = getInvoicePagesFromRow(pageNumber);
        try {
            invoiceList = invoiceDAO.findAllOrdersInfoFromInvoiceByUserId(userId, numberOfInvoicesOnPage, invoiceFromRow);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getAllOrdersInfoFromInvoiceByUserIdFromRow method " + e);
            throw new ServiceException(e);
        }
        return invoiceList;
    }

    @Override
    public List<Invoice> getAllSortedOrdersInfoFromInvoiceByDeliveryAddress(long userId, String deliveryCity, int pageNumber) throws ServiceException {
        logger.log(Level.DEBUG, "Get all sorted order info from invoice by first address:" + userId);
        List<Invoice> invoiceList;
        int invoiceFromRow = getInvoicePagesFromRow(pageNumber);
        try {
            invoiceList = invoiceDAO.findAllOrdersInfoFromInvoiceByDeliveryAddress(userId, deliveryCity, numberOfInvoicesOnPage, invoiceFromRow);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findAllOrdersInfoFromInvoiceByFirstAddressFromRow method " + e);
            throw new ServiceException(e);
        }
        return invoiceList;
    }

    @Override
    public int getNumberOfRowsInInvoiceTableByDeliveryAddress(long userId, String deliveryCity) throws ServiceException {
        logger.log(Level.DEBUG, "Get number of pages by delivery address:");
        int numberOfPages;
        int numberOfOrders;
        try {
            numberOfOrders = invoiceDAO.findNumberOfRowsInInvoiceTableByDeliveryAddress(userId, deliveryCity);
            if (numberOfOrders > numberOfInvoicesOnPage) {
                numberOfPages = (int) Math.ceil((double) numberOfOrders / numberOfInvoicesOnPage);
            } else {
                numberOfPages = 1;
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getNumberOfRowsInInvoiceTableByDeliveryAddress method " + e);
            throw new ServiceException(e);
        }
        return numberOfPages;
    }

    private int getInvoicePagesFromRow(int pageNumber) {
        int invoiceFromRow;
        if (pageNumber > 1) {
            invoiceFromRow = (pageNumber - 1) * numberOfInvoicesOnPage;
        } else {
            invoiceFromRow = 0;
        }
        return invoiceFromRow;
    }


//    @Override
//    public Optional<Invoice> getInvoiceByUserPhone(String userPhone) throws ServiceException {
//        logger.log(Level.DEBUG, "Getting invoice by userPhone:" + userPhone);
//        Optional<Invoice> optionalInvoice;
//        try {
//            if (userPhone == null) {
//                optionalInvoice = Optional.empty();
//                throw new ServiceException("No invoices was found by userPhone " + userPhone);
//            } else {
//                optionalInvoice = invoiceDAO.findInvoiceByUserPhone(userPhone);
//            }
//        } catch (DaoException e) {
//            logger.log(Level.ERROR, "DAO exception in getInvoiceByUserPhone method " + e);
//            throw new ServiceException(e);
//        }
//        return optionalInvoice;
//    }
//
//

    /**
     * Calculation order volume
     * @param calculationData data from order creation page
     * @return order volume
     */
    static BigDecimal calculateDeliveryPrice(Map<String, String> calculationData) throws IncorrectInputException {
        String orderType = calculationData.get(ORDER_TYPE);
        String orderWeight = calculationData.get(ORDER_WEIGHT);
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);
        String deliveryDistance = calculationData.get(DELIVERY_DISTANCE);

        return Calculator.calculateDeliveryPrice(orderType,
                orderWeight,
                orderLength,
                orderHeight,
                orderWidth,
                deliveryDistance);
    }


    /**
     * Calculation order volume
     * @param calculationData data from order creation page
     * @return order volume
     */
    static BigDecimal calculateTotalPrice(Map<String, String> calculationData) throws IncorrectInputException {
        String orderType = calculationData.get(ORDER_TYPE);
        String orderPrice = calculationData.get(ORDER_PRICE);
        String orderWeight = calculationData.get(ORDER_WEIGHT);
        String orderLength = calculationData.get(ORDER_LENGTH);
        String orderHeight = calculationData.get(ORDER_HEIGHT);
        String orderWidth = calculationData.get(ORDER_WIDTH);
        String deliveryDistance = calculationData.get(DELIVERY_DISTANCE);

        return Calculator.calculateTotalServiceDeliveryPrice(orderType,
                orderPrice,
                orderWeight,
                orderLength,
                orderHeight,
                orderWidth,
                deliveryDistance);
    }

    /**
     * Validation input invoiceData to invoice
     * @param invoiceData data for validation
     * @return tru if data valid and false otherwise
     */
    private boolean isInvoiceDataValid(Map<String, String> invoiceData) {
        boolean isValid = false;

        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_PRICE))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_WEIGHT))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_LENGTH))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_HEIGHT))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(ORDER_WIDTH))) {
            isValid = true;
        }
        if (DataValidator.isAllNumbersValid(invoiceData.get(DELIVERY_DISTANCE))) {
            isValid = true;
        }

        return isValid;
    }


    /**
     * @param id
     * @return new User, that has only id
     */
    private User createUser(String id) {
        long userId = Long.parseLong(id);
        return new User.Builder()
                .setUserId(userId)
                .build();
    }


    /**
     * @param id
     * @return new Order, that has only id
     */
    private Order createOrder(String id) {
        long orderId = Long.parseLong(id);
        return new Order.Builder()
                .setOrderId(orderId)
                .build();
    }

    /**
     * @param id
     * @return new Order, that has only id
     */
    private Delivery createDelivery(String id) {
        long deliveryId = Long.parseLong(id);
       return new Delivery.Builder()
                .setDeliveryId(deliveryId)
                .build();
    }

    /**
     * @param id
     * @return new first address, that has only id
     */
    private AddressFirst createFirstAddress(String id) {
        long firstAddressId = Long.parseLong(id);
       return new AddressFirst.Builder()
                .setFirstAddressId(firstAddressId)
                .build();
    }

    /**
     * @param id
     * @return new second address, that has only id
     */
    private AddressSecond createSecondAddress(String id) {
        long secondAddressId = Long.parseLong(id);
        return new AddressSecond.Builder()
                .setSecondAddressId(secondAddressId)
                .build();
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
}
