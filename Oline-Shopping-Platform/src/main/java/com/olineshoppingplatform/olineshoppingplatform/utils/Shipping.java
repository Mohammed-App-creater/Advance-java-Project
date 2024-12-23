package com.olineshoppingplatform.olineshoppingplatform.utils;


import java.sql.Timestamp;

public class Shipping {
    private String trackingNumber;
    private String shippingStatus;
    private Timestamp deliveryDate;

    // Constructors
    public Shipping() {}

    public Shipping(String trackingNumber, String shippingStatus, Timestamp deliveryDate) {
        this.trackingNumber = trackingNumber;
        this.shippingStatus = shippingStatus;
        this.deliveryDate = deliveryDate;
    }

    // Getters and Setters
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
