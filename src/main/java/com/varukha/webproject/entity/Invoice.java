package com.varukha.webproject.entity;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity class Invoice
 *
 * @author Dmytro Varukha
 */

public class Invoice {

    private long invoiceId;
    private Date deliveryDate;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private boolean isDeliveryPaid;
    private Order order;
    private User user;
    private AddressFirst addressFirst;
    private AddressSecond addressSecond;
    private Delivery delivery;
    private Timestamp creationDateTime;
    private OrderStatus orderStatus;

    private Invoice() {

    }

    public enum OrderStatus {
        NOT_PROCESSED ,IN_PROCESSING, ON_THE_WAY, RECEIVED
    }

    public Timestamp getCreationDateTime() {
        return creationDateTime;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }
    public boolean getIsDeliveryPaid() {
        return isDeliveryPaid;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public AddressFirst getAddressFirst() {
        return addressFirst;
    }

    public void setAddressFirst(AddressFirst addressFirst) {
        this.addressFirst = addressFirst;
    }

    public AddressSecond getAddressSecond() {
        return addressSecond;
    }

    public void setAddressSecond(AddressSecond addressSecond) {
        this.addressSecond = addressSecond;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceId == invoice.invoiceId && isDeliveryPaid == invoice.isDeliveryPaid && Objects.equals(deliveryDate, invoice.deliveryDate) && Objects.equals(deliveryPrice, invoice.deliveryPrice) && Objects.equals(totalPrice, invoice.totalPrice) && Objects.equals(order, invoice.order) && Objects.equals(user, invoice.user) && Objects.equals(addressFirst, invoice.addressFirst) && Objects.equals(addressSecond, invoice.addressSecond) && Objects.equals(delivery, invoice.delivery) && Objects.equals(creationDateTime, invoice.creationDateTime) && orderStatus == invoice.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, deliveryDate, deliveryPrice, totalPrice, isDeliveryPaid, order, user, addressFirst, addressSecond, delivery, creationDateTime, orderStatus);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Invoice [InvoiceId = ");
        builder.append(invoiceId);
        builder.append(", deliveryDate = ");
        builder.append(deliveryDate);
        builder.append(", deliveryPrice = ");
        builder.append(deliveryPrice);
        builder.append(", totalPrice = ");
        builder.append(totalPrice);
        builder.append(", isDeliveryPaid = ");
        builder.append(isDeliveryPaid);
        builder.append(", order = ");
        builder.append(order);
        builder.append(", user = ");
        builder.append(user);
        builder.append(", deliveryId = ");
        builder.append(delivery);
        builder.append(", creationDateTime = ");
        builder.append(creationDateTime);
        builder.append(", orderStatus = ");
        builder.append(orderStatus);
        builder.append(", addressFirst = ");
        builder.append(addressFirst);
        builder.append(", addressSecond = ");
        builder.append(addressSecond);
        builder.append("]");
        return builder.toString();
    }

    public static class Builder {
        private final Invoice newInvoice;

        public Builder() {
            newInvoice = new Invoice();
        }

        public Builder setInvoiceId(long invoiceId) {
            newInvoice.invoiceId = invoiceId;
            return this;
        }
        public Builder setDeliveryDate(Date deliveryDate) {
            newInvoice.deliveryDate = deliveryDate;
            return this;
        }

        public Builder setDeliveryPrice(BigDecimal deliveryPrice) {
            newInvoice.deliveryPrice = deliveryPrice;
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            newInvoice.totalPrice = totalPrice;
            return this;
        }

        public Builder setOrder(Order order) {
            newInvoice.order = order;
            return this;
        }

        public Builder setUser(User user) {
            newInvoice.user = user;
            return this;
        }

        public Builder setFirstAddress(AddressFirst addressFirstId) {
            newInvoice.addressFirst = addressFirstId;
            return this;
        }

        public Builder setSecondAddress(AddressSecond addressSecondId) {
            newInvoice.addressSecond = addressSecondId;
            return this;
        }

        public Builder setDelivery(Delivery delivery) {
            newInvoice.delivery = delivery;
            return this;
        }
        public Builder setDeliveryPaymentStatus(boolean isPaid) {
            newInvoice.isDeliveryPaid = isPaid;
            return this;
        }

        public Builder setInvoiceCreationDateTime(Timestamp creationDateTime) {
            newInvoice.creationDateTime = creationDateTime;
            return this;
        }

        public Builder setOrderStatus(OrderStatus orderStatus) {
            newInvoice.orderStatus = orderStatus;
            return this;
        }

        public Invoice build() {
            return newInvoice;
        }

    }
}



