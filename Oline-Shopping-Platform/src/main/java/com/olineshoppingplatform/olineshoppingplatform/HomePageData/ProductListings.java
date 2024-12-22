package com.olineshoppingplatform.olineshoppingplatform.HomePageData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProductListings", value = "/HomeProductListings")
public class ProductListings extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Set headers for CORS and response type
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        res.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setContentType("application/json");

        // Read request body
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        JsonObject jsonObject;
        try {
            jsonObject = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        // Extract parameters (example: category name, page size, and offset)
        String category = jsonObject.get("category").getAsString();
        int pageSize = jsonObject.get("pageSize").getAsInt();
        int offset = jsonObject.get("offset").getAsInt();

        // Fetch products from the database
        try {
            JsonArray products = ProductListingsDB.getProductListings(category, pageSize, offset);
            PrintWriter out = res.getWriter();
            out.write(products.toString());  // Return the products as JSON response
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
