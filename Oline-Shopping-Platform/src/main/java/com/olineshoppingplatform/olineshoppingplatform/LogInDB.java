package com.olineshoppingplatform.olineshoppingplatform;

import java.sql.*;

public class LogInDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oline_shopping_platform_war_exploded";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "199605";

    public static boolean authenticate(String email, String password) throws Exception {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
             }
    }
}
