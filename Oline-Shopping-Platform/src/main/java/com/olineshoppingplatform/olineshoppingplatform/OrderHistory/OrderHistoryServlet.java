package com.olineshoppingplatform.olineshoppingplatform.OrderHistory;

import com.google.gson.Gson;
import com.olineshoppingplatform.olineshoppingplatform.utils.Order;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/orders/history")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); // CORS for development
        response.setHeader("Access-Control-Allow-Methods", "GET");

        PrintWriter out = response.getWriter();

        int customerId;
        try {
            customerId = Integer.parseInt(request.getParameter("customerId")); // Parse customer ID
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Invalid customer ID\"}");
            return;
        }

        OrderDB orderDB = new OrderDB();

        try {
            List<Order> orders = orderDB.getOrderHistory(customerId);

            Gson gson = new Gson();
            String json = gson.toJson(orders);
            response.setStatus(HttpServletResponse.SC_OK);
            out.write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"Unable to fetch order history\"}");
        }
    }
}
