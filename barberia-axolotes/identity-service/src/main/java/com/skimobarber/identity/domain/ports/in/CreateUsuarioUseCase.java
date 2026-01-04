package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Usuario;

public interface CreateUsuarioUseCase {
    Result<Usuario> execute(CreateUsuarioCommand command);

    record CreateUsuarioCommand(
        String nombre,
        String primerApellido,
        String segundoApellido,
        String email,
        String telefono,
        String login,
        String password,
        String rol
    ) {}
}
