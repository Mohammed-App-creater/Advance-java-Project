package com.olineshoppingplatform.olineshoppingplatform.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/oline_shopping_platform_war_exploded";
    private static final String USER = "root";
    private static final String PASSWORD = "199605"; // Replace with your DB password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
