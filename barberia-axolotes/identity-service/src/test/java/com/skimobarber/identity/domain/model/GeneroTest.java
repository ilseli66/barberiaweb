package com.skimobarber.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneroTest {

    @Test
    void shouldCreateGeneroWithAllFields() {
        Genero genero = new Genero(1L, "Masculino", true);

        assertEquals(1L, genero.getId());
        assertEquals("Masculino", genero.getNombre());
        assertTrue(genero.isActivo());
    }

    @Test
    void shouldCreateGeneroWithNoArgsConstructor() {
        Genero genero = new Genero();

        assertNull(genero.getId());
        assertNull(genero.getNombre());
        assertFalse(genero.isActivo());
    }

    @Test
    void shouldSetAndGetId() {
        Genero genero = new Genero();
        genero.setId(5L);

        assertEquals(5L, genero.getId());
    }

    @Test
    void shouldSetAndGetNombre() {
        Genero genero = new Genero();
        genero.setNombre("Femenino");

        assertEquals("Femenino", genero.getNombre());
    }

    @Test
    void shouldSetAndGetActivo() {
        Genero genero = new Genero();
        genero.setActivo(true);

        assertTrue(genero.isActivo());

        genero.setActivo(false);

        assertFalse(genero.isActivo());
    }

    @Test
    void shouldReturnIsActivoCorrectly() {
        Genero generoActivo = new Genero(1L, "Masculino", true);
        Genero generoInactivo = new Genero(2L, "Femenino", false);

        assertTrue(generoActivo.isActivo());
        assertFalse(generoInactivo.isActivo());
    }

    @Test
    void shouldHaveCorrectEqualsAndHashCode() {
        Genero genero1 = new Genero(1L, "Masculino", true);
        Genero genero2 = new Genero(1L, "Masculino", true);
        Genero genero3 = new Genero(2L, "Femenino", true);

        assertEquals(genero1, genero2);
        assertEquals(genero1.hashCode(), genero2.hashCode());
        assertNotEquals(genero1, genero3);
    }

    @Test
    void shouldHaveCorrectToString() {
        Genero genero = new Genero(1L, "Masculino", true);

        String toString = genero.toString();

        assertTrue(toString.contains("Masculino"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("true"));
    }
}
