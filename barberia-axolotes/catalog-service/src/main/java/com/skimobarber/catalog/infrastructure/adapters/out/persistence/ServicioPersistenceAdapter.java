package com.skimobarber.catalog.infrastructure.adapters.out.persistence;

import com.skimobarber.catalog.domain.model.Servicio;
import com.skimobarber.catalog.domain.ports.out.IServicioRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioEntity;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa.JpaServicioRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper.ServicioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioPersistenceAdapter extends JpaBaseAdapter<Servicio, ServicioEntity, Long, JpaServicioRepository> implements IServicioRepository {
    public ServicioPersistenceAdapter(JpaServicioRepository repository) {
        super(repository, ServicioMapper::toDomain, ServicioMapper::toEntity);
    }

    @Override
    public List<Servicio> findByActivo(boolean activo) {
        return repository.findByActivo(activo).stream().map(toDomain).toList();
    }
}
