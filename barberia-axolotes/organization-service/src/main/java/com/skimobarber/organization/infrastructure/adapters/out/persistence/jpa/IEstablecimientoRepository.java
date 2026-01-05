package com.skimobarber.organization.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.organization.domain.model.Establecimiento;

public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Long> {}
