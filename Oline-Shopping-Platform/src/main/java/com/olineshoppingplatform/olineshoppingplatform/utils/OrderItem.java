package com.olineshoppingplatform.olineshoppingplatform.utils;


import java.math.BigDecimal;

public class OrderItem {
    private String productName;
    private String imageUrl;
    private int quantity;
    private BigDecimal price;

    // Constructors
    public OrderItem() {}

    public OrderItem(String productName, String imageUrl, int quantity, BigDecimal price) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

