package com.olineshoppingplatform.olineshoppingplatform;

import com.google.gson.JsonObject;
import com.olineshoppingplatform.olineshoppingplatform.utils.ServletUtils;

import java.io.IOException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "SignUp", value = "/SignUp")
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCorsHeaders(response);
        response.setContentType("application/json");

        JsonObject jsonObject = ServletUtils.parseJsonRequestBody(request, response);
        if (jsonObject == null) return;

        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();

        JsonObject jsonResponse = new JsonObject();
        SignUpDB signUpDB = new SignUpDB();

        try {
            if (!signUpDB.check(email)) {
                signUpDB.insert(name, email, password);
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "User registered successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "User already exists");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred during registration");
        }

        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
