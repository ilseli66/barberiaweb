package com.skimobarber.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    // ========== AGREGAR PUNTOS TESTS ==========

    @Test
    void agregarPuntos_shouldAddPoints_whenPuntosPositivos() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        cliente.agregarPuntos(50);

        assertEquals(150, cliente.getPuntosFidelidad());
    }

    @Test
    void agregarPuntos_shouldNotAddPoints_whenPuntosIsZero() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        cliente.agregarPuntos(0);

        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void agregarPuntos_shouldNotAddPoints_whenPuntosIsNegative() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        cliente.agregarPuntos(-50);

        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void agregarPuntos_shouldAddMultipleTimes() {
        Cliente cliente = new Cliente(1L, 0, null);

        cliente.agregarPuntos(50);
        cliente.agregarPuntos(30);
        cliente.agregarPuntos(20);

        assertEquals(100, cliente.getPuntosFidelidad());
    }

    // ========== CANJEAR PUNTOS TESTS ==========

    @Test
    void canjearPuntos_shouldReturnTrue_andDeductPoints_whenSufficientPoints() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        boolean result = cliente.canjearPuntos(50);

        assertTrue(result);
        assertEquals(50, cliente.getPuntosFidelidad());
    }

    @Test
    void canjearPuntos_shouldReturnTrue_andDeductAllPoints_whenExactAmount() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        boolean result = cliente.canjearPuntos(100);

        assertTrue(result);
        assertEquals(0, cliente.getPuntosFidelidad());
    }

    @Test
    void canjearPuntos_shouldReturnFalse_whenInsufficientPoints() {
        Cliente cliente = new Cliente(1L, 50, "Sin alergias");

        boolean result = cliente.canjearPuntos(100);

        assertFalse(result);
        assertEquals(50, cliente.getPuntosFidelidad());
    }

    @Test
    void canjearPuntos_shouldReturnFalse_whenPuntosIsZero() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        boolean result = cliente.canjearPuntos(0);

        assertFalse(result);
        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void canjearPuntos_shouldReturnFalse_whenPuntosIsNegative() {
        Cliente cliente = new Cliente(1L, 100, "Sin alergias");

        boolean result = cliente.canjearPuntos(-50);

        assertFalse(result);
        assertEquals(100, cliente.getPuntosFidelidad());
    }

    @Test
    void canjearPuntos_shouldReturnFalse_whenClientHasZeroPoints() {
        Cliente cliente = new Cliente(1L, 0, null);

        boolean result = cliente.canjearPuntos(10);

        assertFalse(result);
        assertEquals(0, cliente.getPuntosFidelidad());
    }
}
