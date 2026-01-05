package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.enums.TipoRol;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.CreateUsuarioUseCase;
import com.skimobarber.identity.domain.ports.out.PasswordEncoder;
import com.skimobarber.identity.domain.ports.out.PersonaRepository;
import com.skimobarber.identity.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUsuarioService implements CreateUsuarioUseCase {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUsuarioService(PersonaRepository personaRepository,
                                 UsuarioRepository usuarioRepository,
                                 PasswordEncoder passwordEncoder) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Result<Usuario> execute(CreateUsuarioCommand command) {
        // Validaciones de dominio
        if (command.login() == null || command.login().length() < 5) {
            return Result.validationError("El login debe tener al menos 5 caracteres");
        }

        if (command.password() == null || command.password().length() < 8) {
            return Result.validationError("La contraseña debe tener al menos 8 caracteres");
        }

        // Validar email
        Persona personaValidation = new Persona();
        personaValidation.setEmail(command.email());
        if (!personaValidation.isEmailValid()) {
            return Result.validationError("El email no es válido");
        }

        if (usuarioRepository.existsByLogin(command.login())) {
            return Result.conflict("Ya existe un usuario con ese login");
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

        // Crear Usuario - el password se maneja por separado del modelo de dominio
        TipoRol rol = TipoRol.valueOf(command.rol().toUpperCase());
        Usuario usuario = new Usuario(
            savedPersona.getId(),
            command.login(),
            rol,
            true
        );

        // Encriptar password y guardar
        String encodedPassword = passwordEncoder.encode(command.password());
        Usuario savedUsuario = usuarioRepository.saveWithPassword(usuario, encodedPassword);
        return Result.created(savedUsuario);
    }
}
