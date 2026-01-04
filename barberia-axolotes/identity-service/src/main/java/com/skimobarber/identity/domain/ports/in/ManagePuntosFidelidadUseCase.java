package com.skimobarber.identity.domain.ports.in;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Cliente;

public interface ManagePuntosFidelidadUseCase {
    Result<Cliente> agregarPuntos(Long clienteId, int puntos);
    Result<Cliente> canjearPuntos(Long clienteId, int puntos);
}
