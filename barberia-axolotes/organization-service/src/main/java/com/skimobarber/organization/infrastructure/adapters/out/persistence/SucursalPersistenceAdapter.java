package com.skimobarber.organization.infrastructure.adapters.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.skimobarber.organization.domain.model.Sucursal;
import com.skimobarber.organization.domain.ports.out.ISucursalRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.SucursalEntity;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa.ISucursalJpaRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers.SucursalMapper;

@Component
public class SucursalPersistenceAdapter 
    extends JpaBaseAdapter<Sucursal, SucursalEntity, Long, ISucursalJpaRepository>
    implements ISucursalRepository {
    
    public SucursalPersistenceAdapter(
        ISucursalJpaRepository repository
    ) {
        super(repository, SucursalMapper::toDomain, SucursalMapper::toEntity);
    }

    @Override
    public List<Sucursal> findByEstablecimientoId(Long establecimientoId) {
        return repository.findByEstablecimientoId(establecimientoId)
            .stream()
            .map(toDomain)
            .toList();
    }
}
