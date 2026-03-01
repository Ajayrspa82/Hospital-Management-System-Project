package com.wipro.amazecare.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        // Set status 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Optional: set content type
        response.setContentType("application/json");

        // Send JSON error message
        response.getWriter().write("{\"status\":403," +
                "\"error\":\"Forbidden\"," +
                "\"message\":\"You are not authorized to access this resource\"}");
    }
}