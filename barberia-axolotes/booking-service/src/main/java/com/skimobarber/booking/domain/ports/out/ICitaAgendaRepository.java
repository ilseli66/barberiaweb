package com.skimobarber.booking.domain.ports.out;

import com.skimobarber.booking.domain.model.CitaAgenda;
import com.skimobarber.common.domain.ports.out.IBaseRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ICitaAgendaRepository extends IBaseRepository<CitaAgenda, Long> {
    List<CitaAgenda> findByCitaId(Long citaId);
    List<CitaAgenda> findByEmpleadoIdAndPeriodo(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);
    
    /**
     * Verifica si hay solapamiento de horarios para un empleado
     */
    boolean existsSolapamiento(Long empleadoId, LocalDateTime inicio, LocalDateTime fin);
    
    void deleteByCitaId(Long citaId);
}
