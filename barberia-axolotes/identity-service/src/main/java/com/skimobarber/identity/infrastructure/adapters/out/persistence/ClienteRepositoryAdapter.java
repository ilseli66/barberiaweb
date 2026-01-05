package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.ports.out.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final JpaClienteRepository jpaRepository;
    private final JpaPersonaRepository personaRepository;

    public ClienteRepositoryAdapter(JpaClienteRepository jpaRepository,
                                     JpaPersonaRepository personaRepository) {
        this.jpaRepository = jpaRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        PersonaEntity persona = personaRepository.findById(cliente.getPersonaId())
            .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));

        ClienteEntity entity = ClienteEntity.fromDomain(cliente);
        entity.setPersona(persona);
        ClienteEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Cliente> findByPersonaId(Long personaId) {
        return jpaRepository.findById(personaId).map(ClienteEntity::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll().stream()
            .map(ClienteEntity::toDomain)
            .toList();
    }

    @Override
    public void deleteByPersonaId(Long personaId) {
        jpaRepository.deleteById(personaId);
    }
}
