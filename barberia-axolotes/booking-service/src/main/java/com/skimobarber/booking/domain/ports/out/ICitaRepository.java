package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.time.LocalDate;
import java.util.List;

public interface ICitaRepository extends IBaseRepository<Cita, Long> {
    List<Cita> findByClienteId(Long clienteId);
    List<Cita> findBySucursalIdAndFecha(Long sucursalId, LocalDate fecha);
    List<Cita> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);
    List<Cita> findByEstado(EstadoCita estado);
}
