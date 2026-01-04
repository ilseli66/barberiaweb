package com.skimobarber.booking.domain.ports.out;

/**
 * Cliente Feign para comunicarse con organization-service
 */
public interface OrganizationServiceClient {
    boolean existsSucursal(Long sucursalId);
    boolean existsEmpleado(Long empleadoId);
    boolean empleadoPerteneceASucursal(Long empleadoId, Long sucursalId);
}
