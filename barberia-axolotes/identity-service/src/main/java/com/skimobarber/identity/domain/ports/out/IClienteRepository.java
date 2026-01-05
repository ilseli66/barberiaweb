package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.util.Optional;

public interface IClienteRepository extends IBaseRepository<Cliente, Long> {
    Optional<Cliente> findByPersonaId(Long personaId);
    void deleteByPersonaId(Long personaId);
}
