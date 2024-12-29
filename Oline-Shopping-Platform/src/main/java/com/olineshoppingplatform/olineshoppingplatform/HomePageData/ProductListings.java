package com.olineshoppingplatform.olineshoppingplatform.HomePageData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
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
        ServletUtils.setCorsHeaders(res);

        JsonObject jsonObject = ServletUtils.parseJsonRequestBody(req, res);



        // Extract parameters (example: category name, page size, and offset)
        String category = jsonObject.get("category").getAsString();
        int pageSize = jsonObject.get("pageSize").getAsInt();
        int offset = jsonObject.get("offset").getAsInt();

        // Fetch products from the database
        try {
            System.out.println(category + pageSize + offset + " here 1234");//debug
            JsonArray products = ProductListingsDB.getProductListings(category, pageSize, offset);
            System.out.println(products + " here 1234");//debug
            PrintWriter out = res.getWriter();
            out.write(products.toString());  // Return the products as JSON response
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
