package com.skimobarber.catalog.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.domain.model.ServicioListaPrecio;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

public interface IListaPrecioRepository extends IBaseRepository<ListaPrecio, Long> {
    List<ListaPrecio> findVigentes();
    // Precios de servicios
    void saveServicioListaPrecio(ServicioListaPrecio slp);
    Optional<BigDecimal> findPrecio(Long servicioId, Long listaPrecioId);
    Optional<ServicioListaPrecio> findPrecioVigente(Long servicioId);
}
