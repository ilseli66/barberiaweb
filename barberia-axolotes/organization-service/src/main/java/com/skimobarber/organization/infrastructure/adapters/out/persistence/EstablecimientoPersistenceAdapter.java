package com.skimobarber.organization.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;

import com.skimobarber.organization.domain.model.Establecimiento;
import com.skimobarber.organization.domain.ports.out.IEstablecimientoRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EstablecimientoEntity;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa.IEstablecimientoJpaRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers.EstablecimientoMapper;

@Component
public class EstablecimientoPersistenceAdapter 
    extends JpaBaseAdapter <Establecimiento, EstablecimientoEntity, Long, IEstablecimientoJpaRepository>
    implements IEstablecimientoRepository {
    
    public EstablecimientoPersistenceAdapter(IEstablecimientoJpaRepository jpaRepository) {
        super(jpaRepository, 
              EstablecimientoMapper::toDomain, 
              EstablecimientoMapper::toEntity);
    }
}
