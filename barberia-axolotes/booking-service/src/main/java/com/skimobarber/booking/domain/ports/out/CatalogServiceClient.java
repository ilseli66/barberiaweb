package com.skimobarber.booking.domain.ports.out;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Cliente Feign para comunicarse con catalog-service
 */
public interface CatalogServiceClient {
    Optional<BigDecimal> getPrecioVigente(Long servicioId);
    Optional<Integer> getDuracionServicio(Long servicioId);
    boolean existsServicio(Long servicioId);
}
