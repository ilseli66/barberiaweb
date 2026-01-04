package com.skimobarber.catalog.domain.ports.in;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.common.domain.Result;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ListaPrecioUseCase {
    Result<ListaPrecio> create(CreateListaPrecioCommand command);
    Result<ListaPrecio> findById(Long id);
    Result<List<ListaPrecio>> findAll();
    Result<List<ListaPrecio>> findVigentes();
    Result<ListaPrecio> update(Long id, UpdateListaPrecioCommand command);
    Result<Void> delete(Long id);
    Result<BigDecimal> getPrecioServicio(Long servicioId, Long listaPrecioId);
    Result<BigDecimal> getPrecioVigente(Long servicioId);
    Result<Void> setPrecioServicio(Long servicioId, Long listaPrecioId, BigDecimal precio);

    record CreateListaPrecioCommand(
        String nombre,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
    ) {}

    record UpdateListaPrecioCommand(
        String nombre,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Boolean activo
    ) {}
}
