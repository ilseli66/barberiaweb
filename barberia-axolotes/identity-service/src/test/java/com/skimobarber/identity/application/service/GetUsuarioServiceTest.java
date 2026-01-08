package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @InjectMocks
    private GetUsuarioService service;

    // ========== FIND BY ID TESTS ==========

    @Test
    void findById_shouldReturnNotFound_whenUsuarioDoesNotExist() {
        when(usuarioRepository.findByPersonaId(99L)).thenReturn(Optional.empty());

        Result<Usuario> result = service.findById(99L);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertTrue(result.message().contains("99"));
    }

    @Test
    void findById_shouldReturnUsuario_whenExists() {
        Usuario usuario = new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, true);
        when(usuarioRepository.findByPersonaId(1L)).thenReturn(Optional.of(usuario));

        Result<Usuario> result = service.findById(1L);

        assertTrue(result.isSuccess());
        assertEquals(1L, result.value().getPersonaId());
        assertEquals("admin", result.value().getLogin());
        assertEquals(TipoRol.ADMINISTRADOR, result.value().getRol());
        assertTrue(result.value().isActivo());
    }

    // ========== FIND BY LOGIN TESTS ==========

    @Test
    void findByLogin_shouldReturnNotFound_whenUsuarioDoesNotExist() {
        when(usuarioRepository.findByLogin("nonexistent")).thenReturn(Optional.empty());

        Result<Usuario> result = service.findByLogin("nonexistent");

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.NOT_FOUND, result.failureCategory());
        assertTrue(result.message().contains("nonexistent"));
    }

    @Test
    void findByLogin_shouldReturnUsuario_whenExists() {
        Usuario usuario = new Usuario(5L, "barbero1", TipoRol.EMPLEADO, true);
        when(usuarioRepository.findByLogin("barbero1")).thenReturn(Optional.of(usuario));

        Result<Usuario> result = service.findByLogin("barbero1");

        assertTrue(result.isSuccess());
        assertEquals("barbero1", result.value().getLogin());
        assertEquals(TipoRol.EMPLEADO, result.value().getRol());
    }

    // ========== FIND ALL TESTS ==========

    @Test
    void findAll_shouldReturnEmptyList_whenNoUsuariosExist() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        Result<List<Usuario>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertTrue(result.value().isEmpty());
    }

    @Test
    void findAll_shouldReturnAllUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
            new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, true),
            new Usuario(2L, "barbero1", TipoRol.EMPLEADO, true),
            new Usuario(3L, "cliente1", TipoRol.CLIENTE, true)
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        Result<List<Usuario>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(3, result.value().size());
    }

    @Test
    void findAll_shouldReturnUsuariosWithDifferentRoles() {
        List<Usuario> usuarios = Arrays.asList(
            new Usuario(1L, "admin", TipoRol.ADMINISTRADOR, true),
            new Usuario(2L, "inactivo", TipoRol.CLIENTE, false)
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        Result<List<Usuario>> result = service.findAll();

        assertTrue(result.isSuccess());
        assertEquals(2, result.value().size());
        assertTrue(result.value().get(0).isActivo());
        assertFalse(result.value().get(1).isActivo());
    }
}
