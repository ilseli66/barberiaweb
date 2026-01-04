package com.skimobarber.catalog.domain.ports.out;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.domain.model.ServicioListaPrecio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ListaPrecioRepository {
    ListaPrecio save(ListaPrecio listaPrecio);
    Optional<ListaPrecio> findById(Long id);
    List<ListaPrecio> findAll();
    List<ListaPrecio> findVigentes();
    void deleteById(Long id);
    boolean existsById(Long id);

    // Precios de servicios
    void saveServicioListaPrecio(ServicioListaPrecio slp);
    Optional<BigDecimal> findPrecio(Long servicioId, Long listaPrecioId);
    Optional<ServicioListaPrecio> findPrecioVigente(Long servicioId);
}
