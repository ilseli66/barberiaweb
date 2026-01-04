package com.skimobarber.common.domain;

public record Result<T>(
    T value,
    String message,
    SuccessCategory successCategory,
    FailureCategory failureCategory,
    boolean isSuccess
) {
    public static <T> Result<T> success(T value, SuccessCategory category) {
        return new Result<>(value, null, category, null, true);
    }
    
    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, SuccessCategory.SUCCESS, null, true);
    }

    public static <T> Result<T> created(T value, String message) {
        return new Result<>(value, message, SuccessCategory.CREATED, null, true);
    }

    public static <T> Result<T> created(T value) {
        return new Result<>(value, null, SuccessCategory.CREATED, null, true);
    }

    public static <T> Result<T> accepted(T value, String message) {
        return new Result<>(value, message, SuccessCategory.ACCEPTED, null, true);
    }

    public static <T> Result<T> noContent(String message) {
        return new Result<>(null, message, SuccessCategory.NO_CONTENT, null, true);
    }

    public static <T> Result<T> noContent() {
        return new Result<>(null, null, SuccessCategory.NO_CONTENT, null, true);
    }

    public static <T> Result<T> failure(String message, FailureCategory category) {
        return new Result<>(null, message, null, category, false);
    }

    public static <T> Result<T> notFound(String message) {
        return new Result<>(null, message, null, FailureCategory.NOT_FOUND, false);
    }

    public static <T> Result<T> validationError(String message) {
        return new Result<>(null, message, null, FailureCategory.VALIDATION_ERROR, false);
    }

    public static <T> Result<T> conflict(String message) {
        return new Result<>(null, message, null, FailureCategory.CONFLICT, false);
    }

    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(null, message, null, FailureCategory.UNAUTHORIZED, false);
    }

    public static <T> Result<T> forbidden(String message) {
        return new Result<>(null, message, null, FailureCategory.FORBIDDEN, false);
    }

    public static <T> Result<T> internalError(String message) {
        return new Result<>(null, message, null, FailureCategory.INTERNAL_ERROR, false);
    }

    public static <T> Result<T> unavailable(String message) {
        return new Result<>(null, message, null, FailureCategory.UNAVAILABLE, false);
    }
}
