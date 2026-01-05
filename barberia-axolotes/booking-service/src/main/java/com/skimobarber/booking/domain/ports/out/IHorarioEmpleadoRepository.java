package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface IHorarioEmpleadoRepository extends IBaseRepository<HorarioEmpleado, Long> {
    List<HorarioEmpleado> findByEmpleadoId(Long empleadoId);
    Optional<HorarioEmpleado> findByEmpleadoIdAndDiaSemana(Long empleadoId, DayOfWeek diaSemana);
}
