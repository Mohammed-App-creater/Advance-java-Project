package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;

import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.User;

import java.sql.*;

public class UserDAO {

    // Method to get user by ID
    public User getUserById(int userId) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT * FROM Users WHERE id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        return null;
    }

    // Method to update user information
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE Users SET username = ?, email = ?, password = ? WHERE id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            return ps.executeUpdate() > 0;
        }
    }
}

