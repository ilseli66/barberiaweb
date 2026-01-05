package com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.HorarioEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaHorarioEmpleadoRepository extends JpaRepository<HorarioEmpleadoEntity, Long> {
    List<HorarioEmpleadoEntity> findByEmpleadoId(Long empleadoId);
    Optional<HorarioEmpleadoEntity> findByEmpleadoIdAndDiaSemana(Long empleadoId, Integer diaSemana);
}
