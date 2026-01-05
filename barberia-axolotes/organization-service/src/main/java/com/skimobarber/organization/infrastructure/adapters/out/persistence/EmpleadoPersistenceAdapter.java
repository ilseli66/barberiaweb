package com.skimobarber.organization.infrastructure.adapters.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.domain.ports.out.IEmpleadoRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EmpleadoEntity;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa.IEmpleadoJpaRepository;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers.EmpleadoMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpleadoPersistenceAdapter 
    extends JpaBaseAdapter<Empleado, EmpleadoEntity, Long, IEmpleadoJpaRepository>
    implements IEmpleadoRepository {
        
    public EmpleadoPersistenceAdapter(IEmpleadoJpaRepository repository) {
        super(repository, EmpleadoMapper::toDomain, EmpleadoMapper::toEntity);
    }

    @Override
    public Optional<Empleado> findByPersonaId(Long personaId) {
        return repository.findByPersonaId(personaId).map(toDomain);
    }

    @Override
    public List<Empleado> findBySucursalId(Long sucursalId) {
        return repository.findBySucursalId(sucursalId).stream().map(toDomain).toList();
    }

    @Override
    @Transactional
    public void deleteByPersonaId(Long personaId) {
        repository.deleteByPersonaId(personaId);
    }

    @Override
    public boolean existsByPersonaId(Long personaId) {
        return repository.existsByPersonaId(personaId);
    }
}
