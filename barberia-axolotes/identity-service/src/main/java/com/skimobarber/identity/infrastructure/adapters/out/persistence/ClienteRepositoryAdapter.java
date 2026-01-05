
package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.ports.out.IClienteRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.ClienteEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.PersonaEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaClienteRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaPersonaRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteRepositoryAdapter extends JpaBaseAdapter<Cliente, ClienteEntity, Long, JpaClienteRepository> implements IClienteRepository {
    private final JpaPersonaRepository personaRepository;

    public ClienteRepositoryAdapter(JpaClienteRepository jpaRepository, JpaPersonaRepository personaRepository) {
        super(jpaRepository, ClienteEntity::toDomain, ClienteEntity::fromDomain);
        this.personaRepository = personaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        PersonaEntity persona = personaRepository.findById(cliente.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
        ClienteEntity entity = ClienteEntity.fromDomain(cliente);
        entity.setPersona(persona);
        ClienteEntity saved = repository.save(entity);
        return toDomain.apply(saved);
    }

    @Override
    public Optional<Cliente> findByPersonaId(Long personaId) {
        return repository.findById(personaId).map(toDomain);
    }

    @Override
    public void deleteByPersonaId(Long personaId) {
        repository.deleteById(personaId);
    }
}
