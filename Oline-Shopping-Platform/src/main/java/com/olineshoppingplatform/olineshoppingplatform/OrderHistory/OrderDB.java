package com.olineshoppingplatform.olineshoppingplatform.OrderHistory;



import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import  com.olineshoppingplatform.olineshoppingplatform.utils.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {

    public List<Order> getOrderHistory(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.id AS order_id, o.order_date, o.total_amount, o.status AS order_status, " +
                "s.tracking_number, s.shipping_status " +
                "FROM Orders o " +
                "LEFT JOIN Shipping s ON o.id = s.order_id " +
                "WHERE o.customer_id = ? " +
                "ORDER BY o.order_date DESC";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderDate(resultSet.getTimestamp("order_date"));
                order.setTotalAmount(BigDecimal.valueOf(resultSet.getDouble("total_amount")));
                order.setStatus(resultSet.getString("order_status")); // Use "order_status" alias here
                order.setTrackingNumber(resultSet.getString("tracking_number"));
                order.setShippingStatus(resultSet.getString("shipping_status"));
                orders.add(order);
            }
        }


        return orders;
    }
}
