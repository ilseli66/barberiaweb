package com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.SucursalEntity;

public interface ISucursalJpaRepository extends JpaRepository<SucursalEntity, Long> {
    List<SucursalEntity> findByEstablecimientoId(Long establecimientoId);
}
