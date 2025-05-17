package com.ucentral.microservice.exc.model;

import java.time.LocalDateTime;

public record ApiErrorResponse(

    String path,
    LocalDateTime timestamp,
    Object error

) { }
