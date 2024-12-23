package com.olineshoppingplatform.olineshoppingplatform.CheckOut;


import java.sql.Timestamp;

public class Payment {

    private int id;
    private int orderId;
    private String bankName;
    private String paymentStatus;
    private Timestamp paymentDate;
    private double amount;

    // Constructor
    public Payment(int id, int orderId, String bankName, String paymentStatus, Timestamp paymentDate, double amount) {
        this.id = id;
        this.orderId = orderId;
        this.bankName = bankName;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}

