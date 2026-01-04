package com.skimobarber.booking.domain.ports.out;

/**
 * Cliente Feign para comunicarse con identity-service
 */
public interface IdentityServiceClient {
    boolean existsCliente(Long clienteId);
}
