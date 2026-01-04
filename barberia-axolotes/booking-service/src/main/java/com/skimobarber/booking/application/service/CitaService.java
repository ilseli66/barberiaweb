package com.skimobarber.booking.application.service;

import com.skimobarber.booking.domain.model.Cita;
import com.skimobarber.booking.domain.model.EstadoCita;
import com.skimobarber.booking.domain.ports.in.CitaUseCase;
import com.skimobarber.booking.domain.ports.out.*;
import com.skimobarber.common.domain.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService implements CitaUseCase {

    private final CitaRepository citaRepository;
    private final CitaAgendaRepository agendaRepository;
    private final CatalogServiceClient catalogClient;
    private final IdentityServiceClient identityClient;
    private final OrganizationServiceClient organizationClient;

    public CitaService(CitaRepository citaRepository,
                       CitaAgendaRepository agendaRepository,
                       CatalogServiceClient catalogClient,
                       IdentityServiceClient identityClient,
                       OrganizationServiceClient organizationClient) {
        this.citaRepository = citaRepository;
        this.agendaRepository = agendaRepository;
        this.catalogClient = catalogClient;
        this.identityClient = identityClient;
        this.organizationClient = organizationClient;
    }

    @Override
    @Transactional
    public Result<Cita> crear(CrearCitaCommand command) {
        // Validar cliente existe
        if (!identityClient.existsCliente(command.clienteId())) {
            return Result.notFound("Cliente no encontrado: " + command.clienteId());
        }

        // Validar servicio existe
        if (!catalogClient.existsServicio(command.servicioId())) {
            return Result.notFound("Servicio no encontrado: " + command.servicioId());
        }

        // Validar sucursal existe
        if (!organizationClient.existsSucursal(command.sucursalId())) {
            return Result.notFound("Sucursal no encontrada: " + command.sucursalId());
        }

        // Validar empleado pertenece a sucursal
        if (!organizationClient.empleadoPerteneceASucursal(command.empleadoId(), command.sucursalId())) {
            return Result.validationError("El empleado no pertenece a la sucursal indicada");
        }

        // Obtener precio vigente (congelar)
        BigDecimal precioCongelado = catalogClient.getPrecioVigente(command.servicioId())
            .orElse(BigDecimal.ZERO);

        // Obtener duración del servicio
        Integer duracion = catalogClient.getDuracionServicio(command.servicioId())
            .orElse(30);

        LocalDateTime fin = command.fechaHoraInicio().plusMinutes(duracion);

        // Verificar disponibilidad del empleado
        if (agendaRepository.existsSolapamiento(command.empleadoId(), command.fechaHoraInicio(), fin)) {
            return Result.conflict("El empleado no está disponible en el horario solicitado");
        }

        // Crear cita
        Cita cita = new Cita(
            null,
            command.clienteId(),
            command.servicioId(),
            command.sucursalId(),
            EstadoCita.PROGRAMADA,
            precioCongelado,
            LocalDateTime.now()
        );

        Cita saved = citaRepository.save(cita);
        return Result.created(saved);
    }

    @Override
    public Result<Cita> findById(Long id) {
        return citaRepository.findById(id)
            .map(Result::success)
            .orElseGet(() -> Result.notFound("Cita no encontrada: " + id));
    }

    @Override
    public Result<List<Cita>> findByClienteId(Long clienteId) {
        return Result.success(citaRepository.findByClienteId(clienteId));
    }

    @Override
    public Result<List<Cita>> findBySucursalIdAndFecha(Long sucursalId, LocalDateTime fecha) {
        return Result.success(citaRepository.findBySucursalIdAndFecha(sucursalId, fecha.toLocalDate()));
    }

    @Override
    @Transactional
    public Result<Cita> cancelar(Long id) {
        return citaRepository.findById(id)
            .map(cita -> {
                try {
                    cita.cancelar();
                    Cita saved = citaRepository.save(cita);
                    agendaRepository.deleteByCitaId(id);
                    return Result.success(saved);
                } catch (IllegalStateException e) {
                    return Result.<Cita>validationError(e.getMessage());
                }
            })
            .orElseGet(() -> Result.notFound("Cita no encontrada: " + id));
    }

    @Override
    @Transactional
    public Result<Cita> iniciar(Long id) {
        return citaRepository.findById(id)
            .map(cita -> {
                try {
                    cita.iniciar();
                    Cita saved = citaRepository.save(cita);
                    return Result.success(saved);
                } catch (IllegalStateException e) {
                    return Result.<Cita>validationError(e.getMessage());
                }
            })
            .orElseGet(() -> Result.notFound("Cita no encontrada: " + id));
    }

    @Override
    @Transactional
    public Result<Cita> completar(Long id) {
        return citaRepository.findById(id)
            .map(cita -> {
                try {
                    cita.completar();
                    Cita saved = citaRepository.save(cita);
                    return Result.success(saved);
                } catch (IllegalStateException e) {
                    return Result.<Cita>validationError(e.getMessage());
                }
            })
            .orElseGet(() -> Result.notFound("Cita no encontrada: " + id));
    }
}
