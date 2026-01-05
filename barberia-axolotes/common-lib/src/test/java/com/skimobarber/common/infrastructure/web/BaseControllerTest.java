package com.skimobarber.common.infrastructure.web;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BaseController Result Mapping Tests")
class BaseControllerTest {

    private final BaseController controller = new TestController();

    private static class TestController extends BaseController {
        // Concrete implementation for testing
    }

    @Test
    @DisplayName("Should map success result to 200 OK")
    void shouldMapSuccessToOk() {
        Result<String> result = Result.success("test-value");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().success());
        assertEquals("test-value", response.getBody().data());
        assertNull(response.getBody().error());
    }

    @Test
    @DisplayName("Should map created result to 201 CREATED")
    void shouldMapCreatedTo201() {
        Result<String> result = Result.created("new-resource");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().success());
        assertEquals("new-resource", response.getBody().data());
    }

    @Test
    @DisplayName("Should map accepted result to 202 ACCEPTED")
    void shouldMapAcceptedTo202() {
        Result<String> result = Result.accepted("pending", "Request accepted");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(response.getBody().success());
    }

    @Test
    @DisplayName("Should map no content to 204 NO_CONTENT")
    void shouldMapNoContentTo204() {
        Result<Void> result = Result.noContent();

        ResponseEntity<ApiResponse<Void>> response = controller.mapResult(result);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody().success());
        assertNull(response.getBody().data());
    }

    @Test
    @DisplayName("Should map not found to 404 NOT_FOUND")
    void shouldMapNotFoundTo404() {
        Result<String> result = Result.notFound("Resource not found");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Resource not found", response.getBody().error());
        assertNull(response.getBody().data());
    }

    @Test
    @DisplayName("Should map conflict to 409 CONFLICT")
    void shouldMapConflictTo409() {
        Result<String> result = Result.conflict("Resource already exists");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Resource already exists", response.getBody().error());
    }

    @Test
    @DisplayName("Should map validation error to 400 BAD_REQUEST")
    void shouldMapValidationErrorTo400() {
        Result<String> result = Result.validationError("Invalid input");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Invalid input", response.getBody().error());
    }

    @Test
    @DisplayName("Should map unauthorized to 401 UNAUTHORIZED")
    void shouldMapUnauthorizedTo401() {
        Result<String> result = Result.unauthorized("Authentication required");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Authentication required", response.getBody().error());
    }

    @Test
    @DisplayName("Should map forbidden to 403 FORBIDDEN")
    void shouldMapForbiddenTo403() {
        Result<String> result = Result.forbidden("Access denied");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Access denied", response.getBody().error());
    }

    @Test
    @DisplayName("Should map unavailable to 503 SERVICE_UNAVAILABLE")
    void shouldMapUnavailableTo503() {
        Result<String> result = Result.unavailable("Service unavailable");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Service unavailable", response.getBody().error());
    }

    @Test
    @DisplayName("Should map internal error to 500 INTERNAL_SERVER_ERROR")
    void shouldMapInternalErrorTo500() {
        Result<String> result = Result.internalError("Server error");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().success());
        assertEquals("Server error", response.getBody().error());
    }

    @Test
    @DisplayName("Should handle success result with null message")
    void shouldHandleSuccessWithNullMessage() {
        Result<String> result = Result.success("value");

        ResponseEntity<ApiResponse<String>> response = controller.mapResult(result);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().error());
    }

    @Test
    @DisplayName("ApiResponse should exclude null fields from JSON")
    void apiResponseShouldExcludeNullFieldsFromJson() {
        ApiResponse<String> response = ApiResponse.success("data");

        assertTrue(response.success());
        assertEquals("data", response.data());
        assertNull(response.error());
    }

    @Test
    @DisplayName("ApiResponse failure should exclude null data from JSON")
    void apiResponseFailureShouldExcludeNullData() {
        ApiResponse<String> response = ApiResponse.failure("error message");

        assertFalse(response.success());
        assertEquals("error message", response.error());
        assertNull(response.data());
    }
}
