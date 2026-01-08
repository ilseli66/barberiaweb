package com.skimobarber.identity.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    // ========== EMAIL VALIDATION TESTS ==========

    @Test
    void isEmailValid_shouldReturnFalse_whenEmailIsNull() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", null);

        assertFalse(persona.isEmailValid());
    }

    @Test
    void isEmailValid_shouldReturnFalse_whenEmailDoesNotContainAt() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juangmail.com");

        assertFalse(persona.isEmailValid());
    }

    @Test
    void isEmailValid_shouldReturnFalse_whenEmailIsTooShort() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "a@b");

        assertFalse(persona.isEmailValid());
    }

    @Test
    void isEmailValid_shouldReturnTrue_whenEmailIsValid() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com");

        assertTrue(persona.isEmailValid());
    }

    @Test
    void isEmailValid_shouldReturnTrue_whenEmailIsMinimumValidLength() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "a@b.c");

        assertTrue(persona.isEmailValid());
    }

    // ========== NOMBRE COMPLETO TESTS ==========

    @Test
    void getNombreCompleto_shouldReturnNombreAndPrimerApellido_whenSegundoApellidoIsNull() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", null,
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com");

        assertEquals("Juan García", persona.getNombreCompleto());
    }

    @Test
    void getNombreCompleto_shouldReturnNombreAndPrimerApellido_whenSegundoApellidoIsBlank() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "   ",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com");

        assertEquals("Juan García", persona.getNombreCompleto());
    }

    @Test
    void getNombreCompleto_shouldReturnFullName_whenAllPartsArePresent() {
        Persona persona = new Persona(1L, 1L, "Juan", "García", "López",
            LocalDate.of(1990, 5, 15), "5551234567", "juan@example.com");

        assertEquals("Juan García López", persona.getNombreCompleto());
    }

    @Test
    void getNombreCompleto_shouldReturnNombreAndApellidos_whenSegundoApellidoIsEmpty() {
        Persona persona = new Persona(1L, 1L, "María", "Pérez", "",
            LocalDate.of(1985, 3, 20), "5559876543", "maria@example.com");

        assertEquals("María Pérez", persona.getNombreCompleto());
    }
}
