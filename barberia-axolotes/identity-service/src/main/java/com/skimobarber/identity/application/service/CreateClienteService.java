package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.in.CreateClienteUseCase;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClienteService implements CreateClienteUseCase {

    private final IPersonaRepository personaRepository;
    private final IClienteRepository clienteRepository;

    public CreateClienteService(IPersonaRepository personaRepository,
                                 IClienteRepository clienteRepository) {
        this.personaRepository = personaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Result<Cliente> execute(CreateClienteCommand command) {
        // Validar email
        Persona personaValidation = new Persona();
        personaValidation.setEmail(command.email());
        if (!personaValidation.isEmailValid()) {
            return Result.validationError("El email no es válido");
        }

        if (personaRepository.existsByEmail(command.email())) {
            return Result.conflict("Ya existe una persona con ese email");
        }

        // Crear Persona
        Persona persona = new Persona();
        persona.setNombre(command.nombre());
        persona.setPrimerApellido(command.primerApellido());
        persona.setSegundoApellido(command.segundoApellido());
        persona.setEmail(command.email());
        persona.setTelefono(command.telefono());

        Persona savedPersona = personaRepository.save(persona);

        // Crear Cliente
        Cliente cliente = new Cliente(
            savedPersona.getId(),
            0,
            command.notasAlergias()
        );

        Cliente savedCliente = clienteRepository.save(cliente);
        return Result.created(savedCliente);
    }
}
