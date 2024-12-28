package com.olineshoppingplatform.olineshoppingplatform.Seller;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/seller/dashboard")
public class SellerDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sellerId = (int) request.getSession().getAttribute("sellerId");

        // Retrieve data from DAO
        DashboardDAO dashboardDAO = new DashboardDAO();
        DashboardSummary summary = dashboardDAO.getDashboardSummary(sellerId);

        // Set summary data in the request scope
        request.setAttribute("summary", summary);

        // Forward to the dashboard JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/seller/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
