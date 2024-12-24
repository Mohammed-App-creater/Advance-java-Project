package com.olineshoppingplatform.olineshoppingplatform.utils;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Order {
    private int orderId;
    private String customerName;
    private Date orderDate;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String status;
    private String trackingNumber;
    private String shippingStatus;

    // Constructors
    public Order() {}

    public Order(int orderId, String customerName, Date orderDate, BigDecimal totalAmount, String shippingAddress, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.status = status;
    }

    public Order(int id, int customerId, Timestamp orderDate, BigDecimal totalAmount, String shippingAddress, String status) {
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingNumber() { return trackingNumber; }

    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getShippingStatus() { return shippingStatus; }

    public void setShippingStatus(String shippingStatus) { this.shippingStatus = shippingStatus; }
}
