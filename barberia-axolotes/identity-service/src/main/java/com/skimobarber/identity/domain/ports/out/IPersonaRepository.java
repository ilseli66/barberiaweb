package com.skimobarber.identity.domain.ports.out;

import com.skimobarber.identity.domain.model.Persona;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.util.Optional;

public interface IPersonaRepository extends IBaseRepository<Persona, Long> {
    Optional<Persona> findByEmail(String email);
    boolean existsByEmail(String email);
}
