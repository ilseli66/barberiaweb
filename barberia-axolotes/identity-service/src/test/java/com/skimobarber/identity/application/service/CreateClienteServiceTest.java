package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.FailureCategory;
import com.skimobarber.common.domain.Result;
import com.skimobarber.common.domain.SuccessCategory;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.in.CreateClienteUseCase.CreateClienteCommand;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClienteServiceTest {

    @Mock
    private IPersonaRepository personaRepository;

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private CreateClienteService service;

    @Test
    void shouldReturnValidationErrorWhenEmailIsInvalid() {
        CreateClienteCommand command = new CreateClienteCommand(
            "Ana", "Lopez", "Perez", "bad-email", "555", "Sin alergias"
        );

        Result<Cliente> result = service.execute(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.VALIDATION_ERROR, result.failureCategory());
    }

    @Test
    void shouldReturnConflictWhenEmailExists() {
        CreateClienteCommand command = new CreateClienteCommand(
            "Ana", "Lopez", "Perez", "ana@example.com", "555", "Sin alergias"
        );
        when(personaRepository.existsByEmail("ana@example.com")).thenReturn(true);

        Result<Cliente> result = service.execute(command);

        assertFalse(result.isSuccess());
        assertEquals(FailureCategory.CONFLICT, result.failureCategory());
    }

    @Test
    void shouldCreateClienteWhenDataIsValid() {
        CreateClienteCommand command = new CreateClienteCommand(
            "Ana", "Lopez", "Perez", "ana@example.com", "555", "Sin alergias"
        );
        when(personaRepository.existsByEmail("ana@example.com")).thenReturn(false);

        Persona persona = new Persona();
        persona.setId(20L);
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        Cliente savedCliente = new Cliente(20L, 0, "Sin alergias");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedCliente);

        Result<Cliente> result = service.execute(command);

        assertTrue(result.isSuccess());
        assertEquals(SuccessCategory.CREATED, result.successCategory());
        assertEquals(savedCliente, result.value());
    }
}
