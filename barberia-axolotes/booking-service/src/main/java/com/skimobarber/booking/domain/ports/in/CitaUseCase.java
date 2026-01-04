package com.skimobarber.booking.domain.ports.in;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.common.domain.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaUseCase {
    Result<Cita> crear(CrearCitaCommand command);
    Result<Cita> findById(Long id);
    Result<List<Cita>> findByClienteId(Long clienteId);
    Result<List<Cita>> findBySucursalIdAndFecha(Long sucursalId, LocalDateTime fecha);
    Result<Cita> cancelar(Long id);
    Result<Cita> iniciar(Long id);
    Result<Cita> completar(Long id);

    record CrearCitaCommand(
        Long clienteId,
        Long servicioId,
        Long sucursalId,
        Long empleadoId,
        LocalDateTime fechaHoraInicio
    ) {}
}
