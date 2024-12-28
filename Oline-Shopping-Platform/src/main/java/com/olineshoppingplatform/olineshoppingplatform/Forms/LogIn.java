package com.olineshoppingplatform.olineshoppingplatform.Forms;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogIn", value = "/LogIn")
public class LogIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set CORS headers using utility method
        ServletUtils.setCorsHeaders(response);

        // Parse the request body using utility method
        JsonObject jsonObject = ServletUtils.parseJsonRequestBody(request, response);
        if (jsonObject == null) return; // If parsing failed, response is already handled.

        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();

        PrintWriter out = response.getWriter();

        try {
            System.out.println("Email: " + email + " Password: " + password);
            System.out.println("Authenticating..." + LogInDB.authenticate(email, password));
            if (LogInDB.authenticate(email, password)) {
                out.write("{\"success\": true}");
            } else {
                out.write("{\"success\": false}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"An internal error occurred.\"}");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
