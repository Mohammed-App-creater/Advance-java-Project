package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Method to get orders by customer ID
    public List<Order> getOrdersByCustomerId(int customerId) throws SQLException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE customer_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("order_date"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("shipping_address"),
                        rs.getString("status")
                ));
            }
        }
        return orders;
    }

    // Method to get order by order ID
    public Order getOrderById(int orderId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Orders WHERE id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("order_date"),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("shipping_address"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    // Method to update order status
    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        String query = "UPDATE Orders SET status = ? WHERE id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        }
    }
}
