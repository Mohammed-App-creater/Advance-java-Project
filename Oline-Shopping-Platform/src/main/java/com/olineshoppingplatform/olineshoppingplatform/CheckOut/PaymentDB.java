package com.olineshoppingplatform.olineshoppingplatform.CheckOut;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // Add a new payment entry for an order
    public static boolean addPayment(int orderId, String bankName, double amount) {
        String query = "INSERT INTO Payments (order_id, bank_name, payment_status, amount) VALUES (?, ?, 'pending', ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setString(2, bankName);
            stmt.setDouble(3, amount);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update payment status (e.g., completed, canceled)
    public static boolean updatePaymentStatus(int paymentId, String status) {
        String query = "UPDATE Payments SET payment_status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get payment details by order ID
    public static Payment getPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM Payments WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Fetching column values and handling potential null values
                int id = rs.getInt("id");
                int order_id = rs.getInt("order_id");
                String bankName = rs.getString("bank_name");
                String paymentStatus = rs.getString("payment_status");
                Timestamp paymentDate = rs.getTimestamp("payment_date");
                double amount = rs.getDouble("amount");

                // Check if paymentDate or amount is null
                if (paymentDate == null) {
                    System.out.println("Payment date is null for order " + orderId);
                }
                if (rs.wasNull()) {
                    amount = 0.0; // If amount is null in the DB, set it to 0
                }

                return new Payment(id, order_id, bankName, paymentStatus, paymentDate, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all payments by user ID (if needed)
    public static List<Payment> getPaymentsByUserId(int userId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT p.id, p.order_id, p.payment_status, p.payment_date, p.amount " +
                "FROM Payments p JOIN Orders o ON p.order_id = o.id WHERE o.customer_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("order_id");
                String bankName = rs.getString("bank_name");
                String paymentStatus = rs.getString("payment_status");
                Timestamp paymentDate = rs.getTimestamp("payment_date");
                double amount = rs.getDouble("amount");

                // Handle null values for paymentDate and amount
                if (paymentDate == null) {
                    System.out.println("Payment date is null for user " + userId);
                }
                if (rs.wasNull()) {
                    amount = 0.0;
                }

                Payment payment = new Payment(id, orderId, bankName, paymentStatus, paymentDate, amount);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
