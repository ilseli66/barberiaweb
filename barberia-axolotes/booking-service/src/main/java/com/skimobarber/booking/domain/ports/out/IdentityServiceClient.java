package com.skimobarber.booking.domain.ports.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "identity-service")
public interface IdentityServiceClient {
    @GetMapping("/api/clientes/{clienteId}")
    boolean existsCliente(Long clienteId);
}
