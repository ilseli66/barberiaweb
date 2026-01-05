
package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.identity.domain.ports.out.IPersonaRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.PersonaEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaPersonaRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonaRepositoryAdapter extends JpaBaseAdapter<Persona, PersonaEntity, Long, JpaPersonaRepository> implements IPersonaRepository {

    public PersonaRepositoryAdapter(JpaPersonaRepository jpaRepository) {
        super(jpaRepository, PersonaEntity::toDomain, PersonaEntity::fromDomain);
    }

    @Override
    public Optional<Persona> findByEmail(String email) {
        return repository.findByEmail(email).map(toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
