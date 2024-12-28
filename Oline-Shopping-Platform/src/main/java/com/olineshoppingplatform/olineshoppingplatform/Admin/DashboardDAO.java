package com.olineshoppingplatform.olineshoppingplatform.Admin;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {
    private final Connection connection;

    public DashboardDAO() throws SQLException {
        connection = DBHelper.getConnection();
    }

    public DashboardSummary getDashboardSummary() throws SQLException {
        DashboardSummary summary = new DashboardSummary();

        // Query for total revenue
        String revenueQuery = "SELECT SUM(total_amount) AS totalRevenue FROM orders";
        try (PreparedStatement stmt = connection.prepareStatement(revenueQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                summary.setTotalRevenue(rs.getBigDecimal("totalRevenue"));
            }
        }

        // Query for total orders
        String ordersQuery = "SELECT COUNT(*) AS totalOrders FROM orders";
        try (PreparedStatement stmt = connection.prepareStatement(ordersQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                summary.setTotalOrders(rs.getInt("totalOrders"));
            }
        }

        // Query for total customers
        String customersQuery = "SELECT COUNT(*) AS totalCustomers FROM users WHERE role = 'customer'";
        try (PreparedStatement stmt = connection.prepareStatement(customersQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                summary.setTotalCustomers(rs.getInt("totalCustomers"));
            }
        }

        // Query for total sellers
        String sellersQuery = "SELECT COUNT(*) AS totalSellers FROM users WHERE role = 'seller'";
        try (PreparedStatement stmt = connection.prepareStatement(sellersQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                summary.setTotalSellers(rs.getInt("totalSellers"));
            }
        }

        return summary;
    }
}

