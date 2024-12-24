package com.olineshoppingplatform.olineshoppingplatform.OrderConfermation;


import com.olineshoppingplatform.olineshoppingplatform.utils.OrderItem;
import com.olineshoppingplatform.olineshoppingplatform.utils.Shipping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public Order getOrderDetails(int orderId) {
        String query = """
            SELECT 
                o.id AS order_id, 
                o.order_date, 
                o.total_amount, 
                o.shipping_address, 
                o.status AS order_status, 
                u.username AS customer_name 
            FROM Orders o
            JOIN Users u ON o.customer_id = u.id
            WHERE o.id = ?;
        """;

        Order order = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order();
                    order.setOrderId(resultSet.getInt("order_id"));
                    order.setOrderDate(resultSet.getTimestamp("order_date"));
                    order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                    order.setShippingAddress(resultSet.getString("shipping_address"));
                    order.setStatus(resultSet.getString("order_status"));
                    order.setCustomerName(resultSet.getString("customer_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        String query = """
            SELECT 
                p.name AS product_name, 
                p.image_url, 
                oi.quantity, 
                oi.price AS item_price 
            FROM Order_Items oi
            JOIN Products p ON oi.product_id = p.product_id
            WHERE oi.order_id = ?;
        """;

        List<OrderItem> items = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem item = new OrderItem();
                    item.setProductName(resultSet.getString("product_name"));
                    item.setImageUrl(resultSet.getString("image_url"));
                    item.setQuantity(resultSet.getInt("quantity"));
                    item.setPrice(resultSet.getBigDecimal("item_price"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public Shipping getShippingDetails(int orderId) {
        String query = """
            SELECT 
                tracking_number, 
                shipping_status, 
                delivery_date 
            FROM Shipping
            WHERE order_id = ?;
        """;

        Shipping shipping = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    shipping = new Shipping();
                    shipping.setTrackingNumber(resultSet.getString("tracking_number"));
                    shipping.setShippingStatus(resultSet.getString("shipping_status"));
                    shipping.setDeliveryDate(resultSet.getTimestamp("delivery_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipping;
    }
}
