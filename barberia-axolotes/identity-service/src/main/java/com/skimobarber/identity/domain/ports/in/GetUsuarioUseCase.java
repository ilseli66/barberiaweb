package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Usuario;

import java.util.List;

public interface GetUsuarioUseCase {
    Result<Usuario> findById(Long personaId);
    Result<Usuario> findByLogin(String login);
    Result<List<Usuario>> findAll();
}
