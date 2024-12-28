package com.olineshoppingplatform.olineshoppingplatform.ProductDetailsPage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductDetailsServlet", value = "/ProductDetailsServlet")
public class ProductDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCorsHeaders(response);


        try {
            // Parse the request body as JSON
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            JsonObject requestBody = gson.fromJson(reader, JsonObject.class);

            // Extract productId from JSON body
            int productId = requestBody.get("id").getAsInt();

            // Retrieve product and reviews from database
            Product product = ProductDB.getProductById(productId);
            List<Review> reviews = ProductDB.getProductReviews(productId);


            if (product != null) {
                // Prepare response data
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("product", product);
                responseData.put("reviews", reviews);
                System.out.println(" test" + product );

                // Write JSON response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(responseData));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format");
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
