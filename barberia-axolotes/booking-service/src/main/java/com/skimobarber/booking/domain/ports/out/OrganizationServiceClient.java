package com.skimobarber.booking.domain.ports.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "organization-service")
public interface OrganizationServiceClient {
    @GetMapping("/api/sucursales/{sucursalId}/exists")
    boolean existsSucursal(@PathVariable("sucursalId") Long sucursalId);

    @GetMapping("/api/empleados/{empleadoId}/exists")
    boolean existsEmpleado(@PathVariable("empleadoId") Long empleadoId);

    // FIX: Añadir @PathVariable a AMBOS parámetros
    @GetMapping("/api/empleados/{empleadoId}/sucursal/{sucursalId}/validar")
    boolean empleadoPerteneceASucursal(
        @PathVariable("empleadoId") Long empleadoId, 
        @PathVariable("sucursalId") Long sucursalId
    );
}
