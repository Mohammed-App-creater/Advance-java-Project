package com.olineshoppingplatform.olineshoppingplatform;

import java.sql.*;

public class SignUpDB {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/oline_shopping_platform_war_exploded";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "199605";

    public static void showAll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = con.createStatement()) {

                String sql = "SELECT * FROM User";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println(" ________________________________________________________________________________");
                    System.out.printf("| %-20s | %-30s | %-20s |\n", "Name", "Email", "Password");
                    while (rs.next()) {
                        // Adjust based on your database schema
                        String name = rs.getString(1); // Assuming first column is 'name'
                        String email = rs.getString(2); // Assuming second column is 'email'
                        String password = rs.getString(3); // Assuming third column is 'password'
                        System.out.printf("| %-20s | %-30s | %-20s |\n", name, email, password);
                    }
                    System.out.println("|_______________________________________________________________________________|");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean insert(String name, String email, String password) throws Exception {
        String sql = "INSERT INTO User (name, email, password) VALUES (?, ?, ?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean check(String username) throws Exception {
        String sql = "SELECT * FROM User WHERE name = ?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    System.out.println("IS working true");
                    return true;
                }
                else {
                    System.out.println("IS working fales");
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void checkDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT * FROM User";
                try (Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    while (rs.next()) {
                        System.out.println("User: " + rs.getString("name"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ensure exceptions are printed
        }

    }
}
