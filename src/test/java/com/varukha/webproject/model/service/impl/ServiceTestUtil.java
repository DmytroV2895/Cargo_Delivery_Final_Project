package com.varukha.webproject.model.service.impl;


import com.varukha.webproject.entity.*;

import java.math.BigDecimal;

import static com.varukha.webproject.entity.Invoice.OrderStatus.ON_THE_WAY;


public final class ServiceTestUtil {

    public static User getTestUser() {
        return new User.Builder()
                .setUserId(1L)
                .setName("Victor")
                .setSurname("Bondarenko")
                .setEmail("victor@gmail.com")
                .setPhone("+380668996655")
//                .setRole(User.Role.USER)
                .setPaymentAccount(BigDecimal.valueOf(0))
                .build();
    }

    public static Order getTestOrder() {
       return new Order.Builder()
                .setOrderId(1L)
                .setOrderName("Computer")
                .setOrderType(Order.Type.CARGO)
                .setOrderDescription("New computer")
                .setOrderPrice(BigDecimal.valueOf(15000))
                .setOrderWeight(50)
                .setOrderLength(34)
                .setOrderHeight(67)
                .setOrderWidth(45)
                .setOrderVolume(1025.1)
                .setOrderVolumeWeight(50)
//               .setUserId(getTestUser())
//               .setDeliveryId(getTestDelivery())
                .build();
    }
    public static Delivery getTestDelivery() {
       return new Delivery.Builder()
                .setDeliveryId(1L)
                .setDeliveryType(Delivery.DeliveryType.BY_TRUCK)
                .setDeliveryDistance(1000)
                .setRecipientName("Oleksandr")
                .setRecipientSurname("Pruvalov")
                .setRecipientPhone("+380668774412")
                .build();
    }

    public static AddressFirst getTestAddressFirst() {
        return new AddressFirst.Builder()
                .setFirstAddressId(1L)
                .setFirstCity("SUMY")
                .setFirstStreetName("Mira")
                .setFirstStreetNumber("25")
                .setFirstHouseNumber("56")
                .build();
    }

    public static AddressSecond getTestAddressSecond() {
        return new AddressSecond.Builder()
                .setSecondAddressId(1L)
                .setSecondCity("CHARKIV")
                .setSecondStreetName("Lisna")
                .setSecondStreetNumber("35")
                .setSecondHouseNumber("85")
                .build();
    }

    public static Invoice getTestInvoice() {
        return new Invoice.Builder()
                .setInvoiceId(1L)
                .setDeliveryPrice(BigDecimal.valueOf(600))
                .setTotalPrice(BigDecimal.valueOf(1500))
                .setDeliveryPaymentStatus(true)
                .setOrderStatus(ON_THE_WAY)
                .setDelivery(getTestDelivery())
                .setUser(getTestUser())
                .setOrder(getTestOrder())
                .setFirstAddress(getTestAddressFirst())
                .setSecondAddress(getTestAddressSecond())
                .build();
    }

}
