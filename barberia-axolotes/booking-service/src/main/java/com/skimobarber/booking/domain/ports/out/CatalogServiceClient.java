package com.skimobarber.booking.domain.ports.out;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {
    @GetMapping("/api/listas-precio/servicios/{servicioId}/precio-vigente")
    Optional<BigDecimal> getPrecioVigente(Long servicioId);
    @GetMapping("/api/servicios/{servicioId}/duracion")
    Optional<Integer> getDuracionServicio(Long servicioId);
    @GetMapping("/api/servicios/{servicioId}/exists")
    boolean existsServicio(Long servicioId);
}
