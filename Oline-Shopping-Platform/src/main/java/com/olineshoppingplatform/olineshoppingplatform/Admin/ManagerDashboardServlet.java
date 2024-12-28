package com.olineshoppingplatform.olineshoppingplatform.Admin;



import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ManagerDashboardServlet extends HttpServlet {
    private DashboardDAO dashboardDAO;

    @Override
    public void init() throws ServletException {
        try {
            dashboardDAO = new DashboardDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch dashboard summary
            DashboardSummary summary = dashboardDAO.getDashboardSummary();

            // Set summary data as request attributes
            request.setAttribute("summary", summary);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/manager/dashboard.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load dashboard data.");
        }
    }
}

