package com.varukha.webproject.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entity class Orders
 *
 * @author Dmytro Varukha
 */

public class Order {

    private long orderId;
    private String orderName;
    private Type type;
    private String orderDescription;
    private BigDecimal price;
    private double weight;
    private double length;
    private double height;
    private double width;
    private double volume;
    private double volumeWeight;
    private User userId;
    private Delivery deliveryId;

    private Order() {
    }

    public enum Type {
        CARGO, PARCEL, DOCUMENT
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(double volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Delivery getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Delivery deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Double.compare(order.weight, weight) == 0 && Double.compare(order.length, length) == 0 && Double.compare(order.height, height) == 0 && Double.compare(order.width, width) == 0 && Double.compare(order.volume, volume) == 0 && Double.compare(order.volumeWeight, volumeWeight) == 0 && Objects.equals(orderName, order.orderName) && type == order.type && Objects.equals(orderDescription, order.orderDescription) && Objects.equals(price, order.price) && Objects.equals(userId, order.userId) && Objects.equals(deliveryId, order.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderName, type, orderDescription, price, weight, length, height, width, volume, volumeWeight, userId, deliveryId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [OrderId = ");
        builder.append(orderId);
        builder.append(", orderName = ");
        builder.append(orderName);
        builder.append(", type = ");
        builder.append(type);
        builder.append(", orderDescription = ");
        builder.append(orderDescription);
        builder.append(", price = ");
        builder.append(price);
        builder.append(", weight = ");
        builder.append(weight);
        builder.append(", length = ");
        builder.append(length);
        builder.append(", height = ");
        builder.append(height);
        builder.append(", width = ");
        builder.append(width);
        builder.append(", volume = ");
        builder.append(volume);
        builder.append(", volumeWeight = ");
        builder.append(volumeWeight);
        builder.append(", userId = ");
        builder.append(userId);
        builder.append(", deliveryId = ");
        builder.append(deliveryId);
        builder.append("]");
        return builder.toString();
    }

    public static class Builder {
        private Order newOrder;
        private Type type;

        public Builder() {
            newOrder = new Order();
        }

        public Builder setOrderId(long orderId) {
            newOrder.orderId = orderId;
            return this;
        }

        public Builder setUserId(User userId) {
            newOrder.userId = userId;
            return this;
        }

        public Builder setDeliveryId(Delivery deliveryId) {
            newOrder.deliveryId = deliveryId;
            return this;
        }

        public Builder setOrderName(String orderName) {
            newOrder.orderName = orderName;
            return this;
        }

        public Builder setOrderType(Type type) {
            newOrder.type = type;
            return this;
        }

        public Builder setOrderDescription(String orderDescription) {
            newOrder.orderDescription = orderDescription;
            return this;
        }

        public Builder setOrderPrice(BigDecimal price) {
            newOrder.price = price;
            return this;
        }

        public Builder setOrderWeight(double weight) {
            newOrder.weight = weight;
            return this;
        }

        public Builder setOrderLength(double length) {
            newOrder.length = length;
            return this;
        }

        public Builder setOrderHeight(double height) {
            newOrder.height = height;
            return this;
        }

        public Builder setOrderWidth(double width) {
            newOrder.width = width;
            return this;
        }

        public Builder setOrderVolume(double volume) {
            newOrder.volume = volume;
            return this;
        }

        public Builder setOrderVolumeWeight(double volumeWeight) {
            newOrder.volumeWeight = volumeWeight;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }

}
