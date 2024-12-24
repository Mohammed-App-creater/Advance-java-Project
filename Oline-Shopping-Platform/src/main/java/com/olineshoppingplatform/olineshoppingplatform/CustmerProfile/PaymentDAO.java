package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.Payment;

import java.sql.*;

public class PaymentDAO {

    // Method to get payment details by order ID
    public Payment getPaymentByOrderId(int orderId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Payments WHERE order_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getString("bank_name"),
                        rs.getString("payment_status"),
                        rs.getTimestamp("payment_date"),
                        rs.getBigDecimal("amount")
                );
            }
        }
        return null;
    }

    // Method to update payment status
    public boolean updatePaymentStatus(int paymentId, String status) throws SQLException {
        String query = "UPDATE Payments SET payment_status = ? WHERE id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;
        }
    }
}
