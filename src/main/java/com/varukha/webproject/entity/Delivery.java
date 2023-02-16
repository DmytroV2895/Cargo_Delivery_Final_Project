package com.varukha.webproject.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class Delivery
 * @author Dmytro Varukha
 *
 */

public class Delivery  {

    private long deliveryId;
    private DeliveryType type;
    private int deliveryDistance;
    private String recipientName;
    private String recipientSurname;
    private String recipientPhone;


    private Delivery() {

    }

    public enum DeliveryType {
        BY_TRUCK, COURIER
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public DeliveryType getType() {
        return type;
    }

    public void setType(DeliveryType type) {
        this.type = type;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return deliveryId == delivery.deliveryId && deliveryDistance == delivery.deliveryDistance && type == delivery.type && Objects.equals(recipientName, delivery.recipientName) && Objects.equals(recipientSurname, delivery.recipientSurname) && Objects.equals(recipientPhone, delivery.recipientPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, type, deliveryDistance, recipientName, recipientSurname, recipientPhone);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Delivery [deliveryId = ");
        builder.append(deliveryId);
        builder.append(", type = ");
        builder.append(type);
        builder.append(", deliveryDistance = ");
        builder.append(deliveryDistance);
        builder.append(", recipientName = ");
        builder.append(recipientName);
        builder.append(", recipientSurname = ");
        builder.append(recipientSurname);
        builder.append(", recipientPhone = ");
        builder.append(recipientPhone);
        builder.append("]");
        return builder.toString();
    }

    public static class Builder {
        private Delivery newDelivery;
        private DeliveryType type;

        public Builder() {
            newDelivery = new Delivery();
        }


        public Builder setDeliveryId(long deliveryId) {
            newDelivery.deliveryId = deliveryId;
            return this;
        }

        public Builder setDeliveryDistance(int deliveryDistance) {
            newDelivery.deliveryDistance = deliveryDistance;
            return this;
        }

        public Builder setRecipientName(String recipientName) {
            newDelivery.recipientName = recipientName;
            return this;
        }
        public Builder setRecipientSurname(String recipientSurname) {
            newDelivery.recipientSurname = recipientSurname;
            return this;
        }
        public Builder setRecipientPhone(String recipientPhone) {
            newDelivery.recipientPhone = recipientPhone;
            return this;
        }
        public Builder setDeliveryType(DeliveryType type) {
            newDelivery.type = type;
            return this;
        }

        public Delivery build() {
            return newDelivery;
        }
    }

}
