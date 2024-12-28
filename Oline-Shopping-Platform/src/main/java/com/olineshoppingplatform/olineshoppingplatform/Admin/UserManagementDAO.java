package com.olineshoppingplatform.olineshoppingplatform.Admin;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;
import com.olineshoppingplatform.olineshoppingplatform.utils.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagementDAO {
    private Connection connection;

    public UserManagementDAO() throws SQLException {
        connection = DBHelper.getConnection();
    }

    // Fetch all users
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, email, role FROM users";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }

        return users;
    }

    // Activate/Deactivate user
    public void updateUserStatus(int userId, boolean isActive) throws SQLException {
//        String query = "UPDATE users SET is_active = ? WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setBoolean(1, isActive);
//            stmt.setInt(2, userId);
//            stmt.executeUpdate();
//        }
     }

    // Reset user password
    public void resetUserPassword(int userId, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void resetPassword(int userId, String newPassword) {
    }
}

