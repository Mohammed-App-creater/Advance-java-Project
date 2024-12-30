package com.olineshoppingplatform.olineshoppingplatform.Forms;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import java.sql.*;

public class LogInDB {

    public static boolean authenticate(String email, String password) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DBHelper.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, email);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return false; // Return false if an exception occurs
    }

    public static String getUserId(String email, String password) {
        String sql = "SELECT id FROM users WHERE email = ? AND password = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DBHelper.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, email);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Assuming 'id' is a string, if it's an integer, change to rs.getInt("id")
                        return rs.getString("id"); // Return the user ID as a String
                    } else {
                        return null; // No user found
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return null; // Return null in case of any failure
    }


}
