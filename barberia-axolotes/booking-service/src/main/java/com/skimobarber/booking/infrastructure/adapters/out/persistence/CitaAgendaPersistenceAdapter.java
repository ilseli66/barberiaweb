package com.skimobarber.booking.infrastructure.adapters.out.persistence;

import com.skimobarber.booking.domain.model.CitaAgenda;
import com.skimobarber.booking.domain.ports.out.ICitaAgendaRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaAgendaEntity;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa.JpaCitaAgendaRepository;
import com.skimobarber.booking.infrastructure.adapters.out.persistence.mapper.CitaAgendaMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CitaAgendaPersistenceAdapter extends JpaBaseAdapter<CitaAgenda, CitaAgendaEntity, Long, JpaCitaAgendaRepository> implements ICitaAgendaRepository {
    public CitaAgendaPersistenceAdapter(JpaCitaAgendaRepository repository) {
        super(repository, CitaAgendaMapper::toDomain, CitaAgendaMapper::toEntity);
    }

    @Override
    public List<CitaAgenda> findByCitaId(Long citaId) {
        return repository.findByCitaId(citaId).stream().map(toDomain).toList();
    }

    @Override
    public List<CitaAgenda> findByEmpleadoIdAndPeriodo(Long empleadoId, LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByEmpleadoIdAndPeriodo(empleadoId, inicio, fin).stream().map(toDomain).toList();
    }

    @Override
    public boolean existsSolapamiento(Long empleadoId, LocalDateTime inicio, LocalDateTime fin) {
        return repository.existsSolapamiento(empleadoId, inicio, fin);
    }

    @Override
    @Transactional
    public void deleteByCitaId(Long citaId) {
        repository.deleteByCitaId(citaId);
    }
}
