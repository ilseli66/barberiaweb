package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository {
    Cita save(Cita cita);
    Optional<Cita> findById(Long id);
    List<Cita> findByClienteId(Long clienteId);
    List<Cita> findBySucursalIdAndFecha(Long sucursalId, LocalDate fecha);
    List<Cita> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);
    List<Cita> findByEstado(EstadoCita estado);
    void deleteById(Long id);
}
