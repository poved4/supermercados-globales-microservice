package com.ucentral.microservice.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationHeaderSecurityFilter extends OncePerRequestFilter {

    private final RestClient restClient = RestClient.create();

    @Value("${application.auth.url}")
    private String authServiceUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isBlank()) {
            reject(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
            return;
        }

        try {
            restClient.get()
                .uri(authServiceUrl + "/api/v1/auth")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .toEntity(String.class);

        } catch (HttpClientErrorException e) {
            var error = e.getResponseBodyAsString().replace("{\"error\":\"", "");
            error = error.replace("\"}", "");
            reject(response, HttpServletResponse.SC_UNAUTHORIZED, error);
            return;
        } catch (HttpServerErrorException e) {
            var error = e.getResponseBodyAsString().replace("{\"error\":\"", "");
            error = error.replace("\"}", "");
            reject(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error);
            return;
        } catch (Exception e) {
            reject(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);

    }

    private void reject(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"" + message.replace("\"", "") + "\"}");
    }

}
