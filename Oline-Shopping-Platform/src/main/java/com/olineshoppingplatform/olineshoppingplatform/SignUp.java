package com.olineshoppingplatform.olineshoppingplatform;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "SignUp", value = "/SignUp")
public class SignUp extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set CORS headers
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        SignUpDB sdb = new SignUpDB();

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

        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        PrintWriter out = response.getWriter();

        response.setStatus(HttpServletResponse.SC_OK);
        try {
            sdb.checkDB();
            if (!(sdb.check(email))) {
                System.out.println("User does not exist "+name+" "+email+" "+password);
                sdb.insert(name, email, password);
                out.write("{\"success\": \"User registered successfully\"}");
            }
            else {
                out.write("{\"success\": \"User already exists\"}");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SignUpDB.showAll();
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
