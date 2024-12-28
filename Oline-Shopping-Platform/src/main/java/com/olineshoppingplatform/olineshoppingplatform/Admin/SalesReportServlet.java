package com.olineshoppingplatform.olineshoppingplatform.Admin;




import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SalesReportServlet extends HttpServlet {
    private SalesReportDAO salesReportDAO;

    @Override
    public void init() throws ServletException {
        try {
            salesReportDAO = new SalesReportDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch summary report
            SalesReport summaryReport = salesReportDAO.getSummaryReport();
            request.setAttribute("summaryReport", summaryReport);

            // Fetch sales by category
            List<SalesReport> categoryReports = salesReportDAO.getSalesByCategory();
            request.setAttribute("categoryReports", categoryReports);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/manager/sales_report.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load sales report.");
        }
    }
}

