package com.skimobarber.catalog.domain.ports.in;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.common.domain.Result;

import java.util.List;

public interface ServicioFaseUseCase {
    Result<ServicioFase> create(CreateServicioFaseCommand command);
    Result<List<ServicioFase>> findByServicioId(Long servicioId);
    Result<ServicioFase> update(Long id, UpdateServicioFaseCommand command);
    Result<Void> delete(Long id);
    Result<Integer> calcularDuracionTotal(Long servicioId);

    record CreateServicioFaseCommand(
        Long servicioId,
        Integer orden,
        String nombreFase,
        Integer duracionMinutos,
        boolean requiereEmpleado
    ) {}

    record UpdateServicioFaseCommand(
        Integer orden,
        String nombreFase,
        Integer duracionMinutos,
        Boolean requiereEmpleado
    ) {}
}
