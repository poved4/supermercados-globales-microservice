package com.ucentral.microservice.configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucentral.microservice.authorization.service.AuthService;
import com.ucentral.microservice.exc.model.ApiErrorResponse;
import com.ucentral.microservice.exc.model.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationHeaderSecurityFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/api/v1/auth/signIn"
    );

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }

    private void writeErrorResponse(HttpServletResponse response, HttpServletRequest request, HttpStatus status, Object errorDetail) throws IOException {

        ApiErrorResponse apiError = new ApiErrorResponse(
            request.getRequestURI(),
            LocalDateTime.now(),
            Map.of("message", errorDetail)
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiError));

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isPublicPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            authService.checkToken(request.getHeader(HttpHeaders.AUTHORIZATION));
            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            writeErrorResponse(
                response,
                request,
                HttpStatus.UNAUTHORIZED,
                e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            writeErrorResponse(
                response,
                request,
                HttpStatus.BAD_REQUEST,
                e.getMessage()
            );
        } catch (Exception e) {
            writeErrorResponse(
                response,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error"
            );
        }

    }

}
