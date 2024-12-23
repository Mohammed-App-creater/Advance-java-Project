package com.olineshoppingplatform.olineshoppingplatform.ProductDetailsPage;

import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailsServlet", value = "/ProductDetailsServlet/*")
public class ProductDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCorsHeaders(response);
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // Allow frontend to access the resource
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Allow HTTP methods
        response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Allow specific headers

        int productId = Integer.parseInt(request.getParameter("id"));  // Get product ID from query parameter

        Product product = ProductDB.getProductById(productId);
        List<Review> reviews = ProductDB.getProductReviews(productId);

        if (product != null) {
            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/productDetails.jsp");
            dispatcher.forward(request, response);  // Forward to JSP page for rendering
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

