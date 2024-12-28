package com.olineshoppingplatform.olineshoppingplatform.Admin;



import com.olineshoppingplatform.olineshoppingplatform.utils.DBHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesReportDAO {
    private Connection connection;

    public SalesReportDAO() throws SQLException {
        connection = DBHelper.getConnection();
    }

    // Fetch total revenue and order details
    public SalesReport getSummaryReport() throws SQLException {
        String query = "SELECT " +
                "SUM(total_amount) AS totalRevenue, " +
                "COUNT(*) AS totalOrders, " +
                "AVG(total_amount) AS averageOrderValue " +
                "FROM orders WHERE status = 'Completed'";
        SalesReport report = new SalesReport();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                report.setTotalRevenue(rs.getBigDecimal("totalRevenue"));
                report.setTotalOrders(rs.getInt("totalOrders"));
                report.setAverageOrderValue(rs.getBigDecimal("averageOrderValue"));
            }
        }
        return report;
    }

    // Fetch sales per category
    public List<SalesReport> getSalesByCategory() throws SQLException {
        String query = "SELECT c.name AS productCategory, SUM(oi.quantity * p.price) AS salesPerCategory " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "JOIN categories c ON p.category_id = c.id " +
                "GROUP BY c.name";
        List<SalesReport> categoryReports = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SalesReport report = new SalesReport();
                report.setProductCategory(rs.getString("productCategory"));
                report.setSalesPerCategory(rs.getBigDecimal("salesPerCategory"));
                categoryReports.add(report);
            }
        }
        return categoryReports;
    }
}
