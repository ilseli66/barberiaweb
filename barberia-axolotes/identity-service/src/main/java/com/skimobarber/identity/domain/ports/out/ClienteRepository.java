package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findByPersonaId(Long personaId);
    List<Cliente> findAll();
    void deleteByPersonaId(Long personaId);
}
