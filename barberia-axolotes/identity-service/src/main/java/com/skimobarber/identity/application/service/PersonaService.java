package com.skimobarber.identity.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.in.PersonaUseCase;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;

@Service
public class PersonaService implements PersonaUseCase {
    private final IPersonaRepository personaRepository;

    public PersonaService(IPersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Result<Long> create(CreatePersonaCommand command) {
        Persona persona = new Persona(
            null,
            command.generoId(),
            command.nombre(),
            command.primerApellido(),
            command.segundoApellido(), 
            command.fechaNacimiento(),
            command.telefono(),
            command.email()
        );

        if (!persona.isEmailValid()){
            return Result.failure("Email no tiene un formato valido", null);
        }

        Long personaId = personaRepository.save(persona).getId();

        return Result.success(personaId);
    }

    @Override
    public Result<Persona> findById(Long personaId) {
        Optional<Persona> persona = personaRepository.findById(personaId);
        if (persona.isEmpty()) {
            return Result.failure("Persona no encontrada", null);
        }
        return Result.success(persona.get());
    }

    @Override
    public Result<List<Persona>> findAll() {
        List<Persona> personas = personaRepository.findAll();
        return Result.success(personas);
    }

    @Override
    public Result<Persona> update(Long personaId, UpdatePersonaCommand command) {
        Optional<Persona> existingPersona = personaRepository.findById(personaId);
        if (existingPersona.isEmpty()) {
            return Result.failure("Persona no encontrada", null);
        }

        Persona persona = new Persona(
            personaId,
            command.generoId(),
            command.nombre(),
            command.primerApellido(),
            command.segundoApellido(), 
            command.fechaNacimiento(),
            command.telefono(),
            command.email()
        );

        personaRepository.save(persona);

        return Result.success(persona);
    }

    @Override
    public Result<Void> delete(Long personaId) {
        Optional<Persona> existingPersona = personaRepository.findById(personaId);
        if (existingPersona.isEmpty()) {
            return Result.failure("Persona no encontrada", null);
        }

        personaRepository.deleteById(personaId);

        return Result.success(null);
    }
    
}
