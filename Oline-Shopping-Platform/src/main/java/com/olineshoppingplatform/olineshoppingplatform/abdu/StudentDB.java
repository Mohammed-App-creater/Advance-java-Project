package com.olineshoppingplatform.olineshoppingplatform.abdu;

import java.sql.*;

public class StudentDB {
    private static final String URL = "jdbc:mysql://localhost:3306/astu";
    private static final String USER = "root";
    private static final String PASSWORD = "199605";

    public boolean registerStudent(String name, String email, String password) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

