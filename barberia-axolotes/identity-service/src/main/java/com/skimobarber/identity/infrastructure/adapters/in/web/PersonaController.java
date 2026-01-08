package com.skimobarber.identity.infrastructure.adapters.in.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skimobarber.common.infrastructure.web.ApiResponse;
import com.skimobarber.common.infrastructure.web.BaseController;
import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.in.PersonaUseCase;

@RestController
@RequestMapping("/api/personas")
public class PersonaController extends BaseController {

    private final PersonaUseCase personaUseCase;

    public PersonaController(PersonaUseCase personaUseCase) {
        this.personaUseCase = personaUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@RequestBody CreatePersonaRequest request) {
        var command = new PersonaUseCase.CreatePersonaCommand(
            request.generoId(),
            request.nombre(),
            request.primerApellido(),
            request.segundoApellido(),
            request.fechaNacimiento(),
            request.telefono(),
            request.email()
        );
        return mapResult(personaUseCase.create(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> getById(@PathVariable Long id) {
        return mapResult(personaUseCase.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Persona>>> getAll() {
        return mapResult(personaUseCase.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Persona>> update(
            @PathVariable Long id,
            @RequestBody UpdatePersonaRequest request) {
        var command = new PersonaUseCase.UpdatePersonaCommand(
            request.generoId(),
            request.nombre(),
            request.primerApellido(),
            request.segundoApellido(),
            request.fechaNacimiento(),
            request.telefono(),
            request.email()
        );
        return mapResult(personaUseCase.update(id, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return mapResult(personaUseCase.delete(id));
    }

    public record CreatePersonaRequest(
        Long generoId,
        String nombre,
        String primerApellido,
        String segundoApellido,
        LocalDate fechaNacimiento,
        String telefono,
        String email
    ) {}

    public record UpdatePersonaRequest(
        Long generoId,
        String nombre,
        String primerApellido,
        String segundoApellido,
        LocalDate fechaNacimiento,
        String telefono,
        String email
    ) {}
}
