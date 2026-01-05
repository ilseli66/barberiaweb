package com.skimobarber.booking.infrastructure.adapters.out.persistence.jpa;

import com.skimobarber.booking.infrastructure.adapters.out.persistence.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaCitaRepository extends JpaRepository<CitaEntity, Long> {
    List<CitaEntity> findByClienteId(Long clienteId);
    
    @Query("SELECT c FROM CitaEntity c WHERE c.sucursalId = :sucursalId AND CAST(c.fechaCreacion AS LocalDate) = :fecha")
    List<CitaEntity> findBySucursalIdAndFecha(@Param("sucursalId") Long sucursalId, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT DISTINCT c FROM CitaEntity c JOIN c.agendas a WHERE a.empleadoId = :empleadoId AND CAST(a.inicio AS LocalDate) = :fecha")
    List<CitaEntity> findByEmpleadoIdAndFecha(@Param("empleadoId") Long empleadoId, @Param("fecha") LocalDate fecha);
    
    List<CitaEntity> findByEstado(String estado);
}
