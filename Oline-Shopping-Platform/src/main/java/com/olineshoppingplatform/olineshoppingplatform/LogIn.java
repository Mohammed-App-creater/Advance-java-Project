package com.olineshoppingplatform.olineshoppingplatform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LogIn", value = "/LogIn")
public class LogIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");

        // Read the request body
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Parse JSON from request body
        JsonObject jsonObject;
        try {
            jsonObject = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
            return;
        }

        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();

        PrintWriter out = response.getWriter();


        try {
            System.out.println("Email: " + email + " Password: " + password);
            System.out.println("Authenticating..."+ LogInDB.authenticate(email, password));
            if (LogInDB.authenticate(email, password)) {
                out.write("{\"success\": true}");
            } else {
                out.write("{\"success\": false}");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        out.close();
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
