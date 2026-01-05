package com.skimobarber.booking.infrastructure.adapters.out.persistence;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;
import com.skimobarber.booking.domain.ports.out.ICitaRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaEntity;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa.JpaCitaRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper.CitaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CitaPersistenceAdapter extends JpaBaseAdapter<Cita, CitaEntity, Long, JpaCitaRepository> implements ICitaRepository {
    public CitaPersistenceAdapter(JpaCitaRepository repository) {
        super(repository, CitaMapper::toDomain, CitaMapper::toEntity);
    }

    @Override
    public List<Cita> findByClienteId(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(toDomain).toList();
    }

    @Override
    public List<Cita> findBySucursalIdAndFecha(Long sucursalId, LocalDate fecha) {
        return repository.findBySucursalIdAndFecha(sucursalId, fecha).stream().map(toDomain).toList();
    }

    @Override
    public List<Cita> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha) {
        return repository.findByEmpleadoIdAndFecha(empleadoId, fecha).stream().map(toDomain).toList();
    }

    @Override
    public List<Cita> findByEstado(EstadoCita estado) {
        return repository.findByEstado(estado.name().toLowerCase()).stream().map(toDomain).toList();
    }
}
