package com.skimobarber.identity.application.service;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;
import com.skimobarber.identity.domain.ports.in.GetClienteUseCase;
import com.skimobarber.identity.domain.ports.out.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetClienteService implements GetClienteUseCase {

    private final ClienteRepository clienteRepository;

    public GetClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Result<Cliente> findById(Long personaId) {
        return clienteRepository.findByPersonaId(personaId)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Cliente no encontrado con id: " + personaId));
    }

    @Override
    public Result<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        return Result.success(clientes);
    }
}
