package com.olineshoppingplatform.olineshoppingplatform.Seller;

import java.math.BigDecimal;

public class DashboardSummary {
    private BigDecimal totalSales;
    private int totalOrders;
    private int totalInventory;

    // Getter and Setter for totalSales
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    // Getter and Setter for totalOrders
    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    // Getter and Setter for totalInventory
    public int getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(int totalInventory) {
        this.totalInventory = totalInventory;
    }
}
