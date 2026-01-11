package com.skimobarber.catalog.application.service;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.domain.model.ServicioListaPrecio;
import com.skimobarber.catalog.domain.ports.in.ListaPrecioUseCase;
import com.skimobarber.catalog.domain.ports.out.IListaPrecioRepository;
import com.skimobarber.catalog.domain.ports.out.IServicioRepository;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ListaPrecioService implements ListaPrecioUseCase {

    private final IListaPrecioRepository listaPrecioRepository;
    private final IServicioRepository servicioRepository;

    public ListaPrecioService(IListaPrecioRepository listaPrecioRepository,
                               IServicioRepository servicioRepository) {
        this.listaPrecioRepository = listaPrecioRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional
    public Result<ListaPrecio> create(CreateListaPrecioCommand command) {
        if (command.nombre() == null || command.nombre().isBlank()) {
            return Result.validationError("El nombre de la lista de precios es requerido");
        }

        if (command.fechaInicio() == null) {
            return Result.validationError("La fecha de inicio es requerida");
        }

        ListaPrecio listaPrecio = new ListaPrecio(
            null,
            command.nombre(),
            command.fechaInicio(),
            command.fechaFin(),
            true
        );

        if (!listaPrecio.isFechasValid()) {
            return Result.validationError("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        ListaPrecio saved = listaPrecioRepository.save(listaPrecio);
        return Result.created(saved);
    }

    @Override
    public Result<ListaPrecio> findById(Long id) {
        return listaPrecioRepository.findById(id)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Lista de precios no encontrada con id: " + id));
    }

    @Override
    public Result<List<ListaPrecio>> findAll() {
        return Result.success(listaPrecioRepository.findAll());
    }

    @Override
    public Result<List<ListaPrecio>> findVigentes() {
        return Result.success(listaPrecioRepository.findVigentes());
    }

    @Override
    @Transactional
    public Result<ListaPrecio> update(Long id, UpdateListaPrecioCommand command) {
        return listaPrecioRepository.findById(id)
            .map(listaPrecio -> {
                if (command.nombre() != null) listaPrecio.setNombre(command.nombre());
                if (command.fechaInicio() != null) listaPrecio.setFechaInicio(command.fechaInicio());
                if (command.fechaFin() != null) listaPrecio.setFechaFin(command.fechaFin());
                if (command.activo() != null) listaPrecio.setActivo(command.activo());

                if (!listaPrecio.isFechasValid()) {
                    return Result.<ListaPrecio>validationError("La fecha de fin no puede ser anterior a la fecha de inicio");
                }

                ListaPrecio saved = listaPrecioRepository.save(listaPrecio);
                return Result.success(saved);
            })
            .orElseGet(() -> Result.notFound("Lista de precios no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Result<Void> delete(Long id) {
        if (!listaPrecioRepository.existsById(id)) {
            return Result.notFound("Lista de precios no encontrada con id: " + id);
        }
        listaPrecioRepository.deleteById(id);
        return Result.noContent();
    }

    @Override
    public Result<BigDecimal> getPrecioServicio(Long servicioId, Long listaPrecioId) {
        if (!servicioRepository.existsById(servicioId)) {
            return Result.notFound("Servicio no encontrado con id: " + servicioId);
        }
        if (!listaPrecioRepository.existsById(listaPrecioId)) {
            return Result.notFound("Lista de precios no encontrada con id: " + listaPrecioId);
        }

        return listaPrecioRepository.findPrecio(servicioId, listaPrecioId)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Precio no configurado para el servicio en esta lista"));
    }

    @Override
    public Result<BigDecimal> getPrecioVigente(Long servicioId) {
        if (!servicioRepository.existsById(servicioId)) {
            return Result.notFound("Servicio no encontrado con id: " + servicioId);
        }

        return listaPrecioRepository.findPrecioVigente(servicioId)
            .map(slp -> Result.success(slp.getPrecio()))
            .orElseGet(() -> Result.notFound("No hay precio vigente para el servicio"));
    }

    @Override
    @Transactional
    public Result<Void> setPrecioServicio(Long servicioId, Long listaPrecioId, BigDecimal precio) {
        if (!servicioRepository.existsById(servicioId)) {
            return Result.notFound("Servicio no encontrado con id: " + servicioId);
        }
        if (!listaPrecioRepository.existsById(listaPrecioId)) {
            return Result.notFound("Lista de precios no encontrada con id: " + listaPrecioId);
        }

        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.validationError("El precio debe ser mayor a 0");
        }

        ServicioListaPrecio slp = new ServicioListaPrecio(servicioId, listaPrecioId, precio);
        listaPrecioRepository.saveServicioListaPrecio(slp);
        return Result.noContent();
    }
}
