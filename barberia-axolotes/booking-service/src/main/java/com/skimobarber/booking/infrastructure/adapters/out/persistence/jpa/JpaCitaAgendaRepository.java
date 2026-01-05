package com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaAgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaCitaAgendaRepository extends JpaRepository<CitaAgendaEntity, Long> {
    @Query("SELECT a FROM CitaAgendaEntity a WHERE a.cita.id = :citaId")
    List<CitaAgendaEntity> findByCitaId(@Param("citaId") Long citaId);
    
    @Query("SELECT a FROM CitaAgendaEntity a WHERE a.empleadoId = :empleadoId AND a.inicio >= :inicio AND a.fin <= :fin")
    List<CitaAgendaEntity> findByEmpleadoIdAndPeriodo(@Param("empleadoId") Long empleadoId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM CitaAgendaEntity a WHERE a.empleadoId = :empleadoId AND NOT (a.fin <= :inicio OR a.inicio >= :fin)")
    boolean existsSolapamiento(@Param("empleadoId") Long empleadoId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    @Query("DELETE FROM CitaAgendaEntity a WHERE a.cita.id = :citaId")
    void deleteByCitaId(@Param("citaId") Long citaId);
}
