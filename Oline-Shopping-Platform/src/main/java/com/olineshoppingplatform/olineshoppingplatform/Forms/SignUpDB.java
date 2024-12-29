package com.olineshoppingplatform.olineshoppingplatform.Forms;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import java.sql.*;

public class SignUpDB {

    // Method to insert a new user into the database
    public boolean insert(String name, String email, String password) {
        String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DBHelper.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if a username exists in the database
    public boolean check(String username) {
        String sql = "SELECT username FROM Users WHERE username = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DBHelper.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to validate the database by printing all usernames
    public void checkDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DBHelper.getConnection();
                 Statement stmt = con.createStatement()) {
                String sql = "SELECT username FROM Users";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        System.out.println("User: " + rs.getString("username"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
