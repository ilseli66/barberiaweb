package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;

import java.util.List;

public interface GetClienteUseCase {
    Result<Cliente> findById(Long personaId);
    Result<List<Cliente>> findAll();
}
