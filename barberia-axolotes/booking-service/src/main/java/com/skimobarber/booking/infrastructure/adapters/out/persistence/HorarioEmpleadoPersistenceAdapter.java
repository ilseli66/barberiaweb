package com.skimobarber.booking.infrastructure.adapters.out.persistence;

import com.skimobarber.booking.domain.model.HorarioEmpleado;
import com.skimobarber.booking.domain.ports.out.IHorarioEmpleadoRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.HorarioEmpleadoEntity;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa.JpaHorarioEmpleadoRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper.HorarioEmpleadoMapper;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public class HorarioEmpleadoPersistenceAdapter extends JpaBaseAdapter<HorarioEmpleado, HorarioEmpleadoEntity, Long, JpaHorarioEmpleadoRepository> implements IHorarioEmpleadoRepository {
    public HorarioEmpleadoPersistenceAdapter(JpaHorarioEmpleadoRepository repository) {
        super(repository, HorarioEmpleadoMapper::toDomain, HorarioEmpleadoMapper::toEntity);
    }

    @Override
    public List<HorarioEmpleado> findByEmpleadoId(Long empleadoId) {
        return repository.findByEmpleadoId(empleadoId).stream().map(toDomain).toList();
    }

    @Override
    public Optional<HorarioEmpleado> findByEmpleadoIdAndDiaSemana(Long empleadoId, DayOfWeek diaSemana) {
        return repository.findByEmpleadoIdAndDiaSemana(empleadoId, diaSemana.getValue()).map(toDomain);
    }
}
