package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.GetUsuarioUseCase;
import com.skimobarber.identity.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsuarioService implements GetUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public GetUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Result<Usuario> findById(Long personaId) {
        return usuarioRepository.findByPersonaId(personaId)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Usuario no encontrado con id: " + personaId));
    }

    @Override
    public Result<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Usuario no encontrado con login: " + login));
    }

    @Override
    public Result<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return Result.success(usuarios);
    }
}
