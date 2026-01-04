package com.skimobarber.common.infrastructure.web;

import com.skimobarber.common.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> mapResult(Result<T> result) {
        if (result.isSuccess()) {
            HttpStatus status = switch (result.successCategory()) {
                case CREATED -> HttpStatus.CREATED;
                case ACCEPTED -> HttpStatus.ACCEPTED;
                case NO_CONTENT -> HttpStatus.NO_CONTENT;
                default -> HttpStatus.OK;
            };
            return ResponseEntity.status(status).body(ApiResponse.success(result.value()));
        }

        HttpStatus status = switch (result.failureCategory()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return ResponseEntity.status(status).body(ApiResponse.failure(result.message()));
    }
}
