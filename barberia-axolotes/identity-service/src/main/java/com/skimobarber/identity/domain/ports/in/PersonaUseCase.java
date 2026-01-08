package com.skimobarber.identity.domain.ports.in;

import java.time.LocalDate;
import java.util.List;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Persona;

public interface PersonaUseCase {
    Result<Long> create(CreatePersonaCommand command);
    Result<Persona> findById(Long personaId);
    Result<List<Persona>> findAll();
    Result<Persona> update(Long personaId, UpdatePersonaCommand command);
    Result<Void> delete(Long personaId);

    record CreatePersonaCommand(
        Long generoId, 
        String nombre, 
        String primerApellido, 
        String segundoApellido,
        LocalDate fechaNacimiento, 
        String telefono, 
        String email) {}

    record UpdatePersonaCommand(
        Long generoId, 
        String nombre, 
        String primerApellido, 
        String segundoApellido,
        LocalDate fechaNacimiento, 
        String telefono, 
        String email) {}
}
