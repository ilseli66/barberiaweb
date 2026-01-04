package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;

public interface CreateClienteUseCase {
    Result<Cliente> execute(CreateClienteCommand command);

    record CreateClienteCommand(
        String nombre,
        String primerApellido,
        String segundoApellido,
        String email,
        String telefono,
        String notasAlergias
    ) {}
}
