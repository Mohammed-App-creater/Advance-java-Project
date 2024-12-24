package com.olineshoppingplatform.olineshoppingplatform.CustmerProfile;


import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;

import java.sql.*;

public class InventoryDAO {

    // Method to get inventory for a product
    public int getInventoryByProductId(int productId) throws SQLException, ClassNotFoundException {
        String query = "SELECT quantity_available FROM Inventory WHERE product_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity_available");
            }
        }
        return 0;
    }

    // Method to update product inventory
    public boolean updateInventory(int productId, int quantity) throws SQLException {
        String query = "UPDATE Inventory SET quantity_available = ? WHERE product_id = ?";
        try (Connection con = DBHelper.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }
}
