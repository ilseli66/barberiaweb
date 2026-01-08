package com.skimobarber.identity.domain.model;

import com.skimobarber.identity.domain.enums.TipoRol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    // ========== IS ACTIVO TESTS ==========

    @Test
    void isActivo_shouldReturnTrue_whenUsuarioIsActive() {
        Usuario usuario = new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, true);

        assertTrue(usuario.isActivo());
    }

    @Test
    void isActivo_shouldReturnFalse_whenUsuarioIsInactive() {
        Usuario usuario = new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, false);

        assertFalse(usuario.isActivo());
    }

    // ========== IS LOGIN VALID TESTS ==========

    @Test
    void isLoginValid_shouldReturnFalse_whenLoginIsNull() {
        Usuario usuario = new Usuario(1L, null, TipoRol.CLIENTE, true);

        assertFalse(usuario.isLoginValid());
    }

    @Test
    void isLoginValid_shouldReturnFalse_whenLoginIsTooShort() {
        Usuario usuario = new Usuario(1L, "abc", TipoRol.CLIENTE, true);

        assertFalse(usuario.isLoginValid());
    }

    @Test
    void isLoginValid_shouldReturnFalse_whenLoginHasFourCharacters() {
        Usuario usuario = new Usuario(1L, "abcd", TipoRol.CLIENTE, true);

        assertFalse(usuario.isLoginValid());
    }

    @Test
    void isLoginValid_shouldReturnTrue_whenLoginHasFiveCharacters() {
        Usuario usuario = new Usuario(1L, "abcde", TipoRol.CLIENTE, true);

        assertTrue(usuario.isLoginValid());
    }

    @Test
    void isLoginValid_shouldReturnTrue_whenLoginIsLong() {
        Usuario usuario = new Usuario(1L, "usuario_largo_valido", TipoRol.CLIENTE, true);

        assertTrue(usuario.isLoginValid());
    }

    // ========== CAN ACCUMULATE POINTS TESTS ==========

    @Test
    void canAccumulatePoints_shouldReturnTrue_whenRolIsCliente() {
        Usuario usuario = new Usuario(1L, "cliente1", TipoRol.CLIENTE, true);

        assertTrue(usuario.canAccumulatePoints());
    }

    @Test
    void canAccumulatePoints_shouldReturnFalse_whenRolIsAdmin() {
        Usuario usuario = new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, true);

        assertFalse(usuario.canAccumulatePoints());
    }

    @Test
    void canAccumulatePoints_shouldReturnFalse_whenRolIsEmpleado() {
        Usuario usuario = new Usuario(1L, "barbero1", TipoRol.EMPLEADO, true);

        assertFalse(usuario.canAccumulatePoints());
    }

    @Test
    void canAccumulatePoints_shouldReturnTrue_evenWhenUsuarioIsInactive() {
        Usuario usuario = new Usuario(1L, "cliente_inactivo", TipoRol.CLIENTE, false);

        assertTrue(usuario.canAccumulatePoints());
    }
}
