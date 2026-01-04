package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.CitaAgenda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaAgendaRepository {
    CitaAgenda save(CitaAgenda agenda);
    Optional<CitaAgenda> findById(Long id);
    List<CitaAgenda> findByCitaId(Long citaId);
    List<CitaAgenda> findByEmpleadoIdAndPeriodo(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Verifica si hay solapamiento de horarios para un empleado
     */
    boolean existsSolapamiento(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);
    
    void deleteByCitaId(Long citaId);
}
