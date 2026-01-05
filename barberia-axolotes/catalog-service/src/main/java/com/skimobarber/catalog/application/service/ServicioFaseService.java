package com.skimobarber.catalog.application.service;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.domain.ports.in.ServicioFaseUseCase;
import com.skimobarber.catalog.domain.ports.out.IServicioFaseRepository;
import com.skimobarber.catalog.domain.ports.out.IServicioRepository;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioFaseService implements ServicioFaseUseCase {

    private final IServicioFaseRepository faseRepository;
    private final IServicioRepository servicioRepository;

    public ServicioFaseService(IServicioFaseRepository faseRepository,
                                IServicioRepository servicioRepository) {
        this.faseRepository = faseRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional
    public Result<ServicioFase> create(CreateServicioFaseCommand command) {
        if (!servicioRepository.existsById(command.servicioId())) {
            return Result.notFound("Servicio no encontrado con id: " + command.servicioId());
        }

        if (command.duracionMinutos() == null || command.duracionMinutos() <= 0) {
            return Result.validationError("La duración debe ser mayor a 0 minutos");
        }

        ServicioFase fase = new ServicioFase(
            null,
            command.servicioId(),
            command.orden(),
            command.nombreFase(),
            command.duracionMinutos(),
            command.requiereEmpleado()
        );

        ServicioFase saved = faseRepository.save(fase);
        return Result.created(saved);
    }

    @Override
    public Result<List<ServicioFase>> findByServicioId(Long servicioId) {
        return Result.success(faseRepository.findByServicioIdOrderByOrden(servicioId));
    }

    @Override
    @Transactional
    public Result<ServicioFase> update(Long id, UpdateServicioFaseCommand command) {
        return faseRepository.findById(id)
            .map(fase -> {
                if (command.orden() != null) fase.setOrden(command.orden());
                if (command.nombreFase() != null) fase.setNombreFase(command.nombreFase());
                if (command.duracionMinutos() != null) fase.setDuracionMinutos(command.duracionMinutos());
                if (command.requiereEmpleado() != null) fase.setRequiereEmpleado(command.requiereEmpleado());
                ServicioFase saved = faseRepository.save(fase);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Fase no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!faseRepository.existsById(id)) {
            return Result.notFound("Fase no encontrada con id: " + id);
        }
        faseRepository.deleteById(id);
        return Result.noContent();
    }

    @Override
    public Result<Integer> calcularDuracionTotal(Long servicioId) {
        List<ServicioFase> fases = faseRepository.findByServicioIdOrderByOrden(servicioId);
        int duracionTotal = fases.stream()
            .mapToInt(ServicioFase::getDuracionMinutos)
            .sum();
        return Result.success(duracionTotal);
    }
}
