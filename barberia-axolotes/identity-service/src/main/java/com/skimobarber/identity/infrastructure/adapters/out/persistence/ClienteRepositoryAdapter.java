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

        ClienteEntity entity = toEntity(cliente);
        entity.setPersona(persona);
        ClienteEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Cliente> findByPersonaId(Long personaId) {
        return jpaRepository.findById(personaId).map(this::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public void deleteByPersonaId(Long personaId) {
        jpaRepository.deleteById(personaId);
    }

    private ClienteEntity toEntity(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setPersonaId(cliente.getPersonaId());
        entity.setPuntosFidelidad(cliente.getPuntosFidelidad());
        entity.setNotasAlergias(cliente.getNotasAlergias());
        return entity;
    }

    private Cliente toDomain(ClienteEntity entity) {
        return new Cliente(
            entity.getPersonaId(),
            entity.getPuntosFidelidad(),
            entity.getNotasAlergias()
        );
    }
}
