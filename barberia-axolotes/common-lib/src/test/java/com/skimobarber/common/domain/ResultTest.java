package com.skimobarber.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Result Value Object Tests")
class ResultTest {

    @Test
    @DisplayName("Should create success result with value")
    void shouldCreateSuccessResult() {
        Result<String> result = Result.success("test-value");

        assertTrue(result.isSuccess());
        assertEquals("test-value", result.value());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
        assertNull(result.failureCategory());
    }

    @Test
    @DisplayName("Should create success result with specific category")
    void shouldCreateSuccessResultWithCategory() {
        Result<String> result = Result.success("test", SuccessCategory.ACCEPTED);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.ACCEPTED, result.successCategory());
    }

    @Test
    @DisplayName("Should create created result")
    void shouldCreateCreatedResult() {
        Result<String> result = Result.created("new-value");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals("new-value", result.value());
    }

    @Test
    @DisplayName("Should create created result with message")
    void shouldCreateCreatedResultWithMessage() {
        Result<String> result = Result.created("new-value", "Resource created successfully");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals("Resource created successfully", result.message());
    }

    @Test
    @DisplayName("Should create accepted result")
    void shouldCreateAcceptedResult() {
        Result<String> result = Result.accepted("value", "Request accepted");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.ACCEPTED, result.successCategory());
        assertEquals("Request accepted", result.message());
    }

    @Test
    @DisplayName("Should create no content result without message")
    void shouldCreateNoContentResultWithoutMessage() {
        Result<Void> result = Result.noContent();

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        assertNull(result.value());
        assertNull(result.message());
    }

    @Test
    @DisplayName("Should create no content result with message")
    void shouldCreateNoContentResultWithMessage() {
        Result<Void> result = Result.noContent("Deleted successfully");

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.NO_CONTENT, result.successCategory());
        assertEquals("Deleted successfully", result.message());
    }

    @Test
    @DisplayName("Should create not found failure")
    void shouldCreateNotFoundFailure() {
        Result<String> result = Result.notFound("Resource not found");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertEquals("Resource not found", result.message());
        assertNull(result.value());
    }

    @Test
    @DisplayName("Should create validation error failure")
    void shouldCreateValidationErrorFailure() {
        Result<String> result = Result.validationError("Invalid input");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
        assertEquals("Invalid input", result.message());
    }

    @Test
    @DisplayName("Should create conflict failure")
    void shouldCreateConflictFailure() {
        Result<String> result = Result.conflict("Resource already exists");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
        assertEquals("Resource already exists", result.message());
    }

    @Test
    @DisplayName("Should create unauthorized failure")
    void shouldCreateUnauthorizedFailure() {
        Result<String> result = Result.unauthorized("Authentication required");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.UNAUTHORIZED, result.failureCategory());
        assertEquals("Authentication required", result.message());
    }

    @Test
    @DisplayName("Should create forbidden failure")
    void shouldCreateForbiddenFailure() {
        Result<String> result = Result.forbidden("Access denied");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.FORBIDDEN, result.failureCategory());
        assertEquals("Access denied", result.message());
    }

    @Test
    @DisplayName("Should create internal error failure")
    void shouldCreateInternalErrorFailure() {
        Result<String> result = Result.internalError("Server error");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.INTERNAL_ERROR, result.failureCategory());
        assertEquals("Server error", result.message());
    }

    @Test
    @DisplayName("Should create unavailable failure")
    void shouldCreateUnavailableFailure() {
        Result<String> result = Result.unavailable("Service unavailable");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.UNAVAILABLE, result.failureCategory());
        assertEquals("Service unavailable", result.message());
    }

    @Test
    @DisplayName("Should create generic failure with custom category")
    void shouldCreateGenericFailure() {
        Result<String> result = Result.failure("Custom error", FailureCategory.NOT_FOUND);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertEquals("Custom error", result.message());
    }

    @Test
    @DisplayName("Should handle null value in success result")
    void shouldHandleNullValueInSuccessResult() {
        Result<String> result = Result.success(null);

        assertTrue(result.isSuccess());
        assertNull(result.value());
        assertEquals(SuccessCategory.SUCCESS, result.successCategory());
    }

    @Test
    @DisplayName("Success result should have null failure category")
    void successResultShouldHaveNullFailureCategory() {
        Result<String> result = Result.success("value");

        assertNull(result.failureCategory());
    }

    @Test
    @DisplayName("Failure result should have null success category")
    void failureResultShouldHaveNullSuccessCategory() {
        Result<String> result = Result.notFound("error");

        assertNull(result.successCategory());
    }
}
