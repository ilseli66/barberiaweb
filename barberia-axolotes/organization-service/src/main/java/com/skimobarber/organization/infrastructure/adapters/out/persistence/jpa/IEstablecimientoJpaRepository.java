package com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EstablecimientoEntity;

public interface IEstablecimientoJpaRepository extends JpaRepository<EstablecimientoEntity, Long> {}
