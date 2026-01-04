package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.HorarioEmpleado;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface HorarioEmpleadoRepository {
    HorarioEmpleado save(HorarioEmpleado horario);
    Optional<HorarioEmpleado> findById(Long id);
    List<HorarioEmpleado> findByEmpleadoId(Long empleadoId);
    Optional<HorarioEmpleado> findByEmpleadoIdAndDiaSemana(Long empleadoId, DayOfWeek diaSemana);
    void deleteById(Long id);
    boolean existsById(Long id);
}
