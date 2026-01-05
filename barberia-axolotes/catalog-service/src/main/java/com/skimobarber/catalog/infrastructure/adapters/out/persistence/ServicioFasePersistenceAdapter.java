package com.skimobarber.catalog.infrastructure.adapters.out.persistence;

import com.skimobarber.catalog.domain.model.ServicioFase;
import com.skimobarber.catalog.domain.ports.out.IServicioFaseRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioFaseEntity;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa.JpaServicioFaseRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper.ServicioFaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioFasePersistenceAdapter extends JpaBaseAdapter<ServicioFase, ServicioFaseEntity, Long, JpaServicioFaseRepository> implements IServicioFaseRepository {
    public ServicioFasePersistenceAdapter(JpaServicioFaseRepository repository) {
        super(repository, ServicioFaseMapper::toDomain, ServicioFaseMapper::toEntity);
    }

    @Override
    public List<ServicioFase> findByServicioIdOrderByOrden(Long servicioId) {
        return repository.findByServicioIdOrderByOrden(servicioId).stream().map(toDomain).toList();
    }
}
