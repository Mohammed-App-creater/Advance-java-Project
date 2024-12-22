package com.olineshoppingplatform.olineshoppingplatform;

import java.sql.*;

public class SignUpDB {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/oline_shopping_platform_war_exploded";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "199605";

    // Method to insert a new user into the database
    public boolean insert(String name, String email, String password) {
        String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
