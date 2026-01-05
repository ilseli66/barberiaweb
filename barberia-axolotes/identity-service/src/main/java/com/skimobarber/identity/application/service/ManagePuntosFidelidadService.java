package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.in.ManagePuntosFidelidadUseCase;
import com.skimobarber.identity.domain.ports.out.ClienteRepository;
import com.skimobarber.identity.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagePuntosFidelidadService implements ManagePuntosFidelidadUseCase {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ManagePuntosFidelidadService(ClienteRepository clienteRepository,
                                         UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Result<Cliente> agregarPuntos(Long clienteId, int puntos) {
        if (puntos <= 0) {
            return Result.validationError("Los puntos deben ser mayores a 0");
        }

        // Verificar que el usuario tiene rol CLIENTE (regla de negocio)
        Usuario usuario = usuarioRepository.findByPersonaId(clienteId).orElse(null);
        if (usuario != null && !usuario.canAccumulatePoints()) {
            return Result.validationError("Solo los clientes pueden acumular puntos de fidelidad");
        }

        return clienteRepository.findByPersonaId(clienteId)
            .map(cliente -> {
                cliente.agregarPuntos(puntos);
                Cliente saved = clienteRepository.save(cliente);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Cliente no encontrado con id: " + clienteId));
    }

    @Override
    @Transactional
    public Result<Cliente> canjearPuntos(Long clienteId, int puntos) {
        if (puntos <= 0) {
            return Result.validationError("Los puntos deben ser mayores a 0");
        }

        return clienteRepository.findByPersonaId(clienteId)
            .map(cliente -> {
                if (!cliente.canjearPuntos(puntos)) {
                    return Result.<Cliente>validationError("Puntos insuficientes para canjear");
                }
                Cliente saved = clienteRepository.save(cliente);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Cliente no encontrado con id: " + clienteId));
    }
}
