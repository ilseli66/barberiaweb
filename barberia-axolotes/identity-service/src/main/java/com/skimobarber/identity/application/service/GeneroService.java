package com.skimobarber.identity.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Genero;
import com.skimobarber.identity.domain.ports.in.GeneroUseCase;
import com.skimobarber.identity.domain.ports.out.IGeneroRepository;

@Service
public class GeneroService implements GeneroUseCase {

    private final IGeneroRepository generoRepository;

    public GeneroService(IGeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    @Transactional
    public Result<Genero> create(CreateGeneroCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            return Result.validationError("El nombre del género es requerido");
        }

        Genero genero = new Genero();
        genero.setNombre(command.nombre());
        genero.setActivo(true);

        Genero savedGenero = generoRepository.save(genero);
        return Result.created(savedGenero);
    }

    @Override
    public Result<Genero> findById(Long id) {
        Optional<Genero> genero = generoRepository.findById(id);
        if (genero.isEmpty()) {
            return Result.notFound("Género no encontrado con id: " + id);
        }
        return Result.success(genero.get());
    }

    @Override
    public Result<List<Genero>> findAll() {
        List<Genero> generos = generoRepository.findAll();
        return Result.success(generos);
    }

    @Override
    public Result<List<Genero>> findAllActivos() {
        List<Genero> generos = generoRepository.findAll().stream()
            .filter(Genero::isActivo)
            .collect(Collectors.toList());
        return Result.success(generos);
    }

    @Override
    @Transactional
    public Result<Genero> update(Long id, UpdateGeneroCommand command) {
        Optional<Genero> existingGenero = generoRepository.findById(id);
        if (existingGenero.isEmpty()) {
            return Result.notFound("Género no encontrado con id: " + id);
        }

        if (command.nombre() == null || command.nombre().isBlank()) {
            return Result.validationError("El nombre del género es requerido");
        }

        Genero genero = existingGenero.get();
        genero.setNombre(command.nombre());
        genero.setActivo(command.activo());

        Genero savedGenero = generoRepository.save(genero);
        return Result.success(savedGenero);
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        Optional<Genero> existingGenero = generoRepository.findById(id);
        if (existingGenero.isEmpty()) {
            return Result.notFound("Género no encontrado con id: " + id);
        }

        generoRepository.deleteById(id);
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result<Genero> activate(Long id) {
        Optional<Genero> existingGenero = generoRepository.findById(id);
        if (existingGenero.isEmpty()) {
            return Result.notFound("Género no encontrado con id: " + id);
        }

        Genero genero = existingGenero.get();
        genero.setActivo(true);

        Genero savedGenero = generoRepository.save(genero);
        return Result.success(savedGenero);
    }

    @Override
    @Transactional
    public Result<Genero> deactivate(Long id) {
        Optional<Genero> existingGenero = generoRepository.findById(id);
        if (existingGenero.isEmpty()) {
            return Result.notFound("Género no encontrado con id: " + id);
        }

        Genero genero = existingGenero.get();
        genero.setActivo(false);

        Genero savedGenero = generoRepository.save(genero);
        return Result.success(savedGenero);
    }
}
