package com.olineshoppingplatform.olineshoppingplatform.CheckOut;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment", "/payment/*"})
public class PaymentServlet extends HttpServlet {

    // Handling POST request to add payment
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Set response type to JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONObject jsonResponse = new JSONObject();

        if ("add".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("order_id"));
            String bankName = request.getParameter("bank_name");
            double amount = Double.parseDouble(request.getParameter("amount"));

            // Call DAO to add payment
            boolean isAdded = PaymentDB.addPayment(orderId, bankName, amount);

            if (isAdded) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Payment added successfully.");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error: Payment could not be added.");
            }
        } else if ("updateStatus".equals(action)) {
            int paymentId = Integer.parseInt(request.getParameter("payment_id"));
            String paymentStatus = request.getParameter("payment_status");

            // Call DAO to update payment status
            boolean isUpdated = PaymentDB.updatePaymentStatus(paymentId, paymentStatus);

            if (isUpdated) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Payment status updated.");
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error: Payment status could not be updated.");
            }
        }

        // Send JSON response back to the React frontend
        out.print(jsonResponse.toString());
        out.flush();
    }

    // Handling GET request to retrieve payment info
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Set response type to JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JSONObject jsonResponse = new JSONObject();

        if ("getPaymentByOrderId".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Payment payment = PaymentDB.getPaymentByOrderId(orderId);

            if (payment != null) {
                jsonResponse.put("status", "success");
                jsonResponse.put("payment", payment);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error: No payment information found for this order.");
            }
        } else if ("getPaymentsByUser".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Payment> payments = PaymentDB.getPaymentsByUserId(userId);

            if (payments != null && !payments.isEmpty()) {
                jsonResponse.put("status", "success");
                jsonResponse.put("payments", payments);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error: No payments found for this user.");
            }
        }

        // Send JSON response back to the React frontend
        out.print(jsonResponse.toString());
        out.flush();
    }
}
