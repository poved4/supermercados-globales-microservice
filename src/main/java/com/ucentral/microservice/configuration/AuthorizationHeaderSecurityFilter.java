package com.ucentral.microservice.configuration;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ucentral.microservice.authorization.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationHeaderSecurityFilter extends OncePerRequestFilter {

    private final AuthService authService;

    private static final Set<String> PUBLIC_PATHS = Set.of("/auth", "/auth/login", "/auth/register");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isBlank()) {
            reject(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing Authorization header");
            return;
        }

        boolean isAuthenticated = authService.validateToken(authHeader);
        if (isAuthenticated) {
            filterChain.doFilter(request, response);
        } else {
            reject(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired token");
            return;
        }

    }

    private void reject(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"" + message.replace("\"", "") + "\"}");
    }

}
