package com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.GeneroEntity;

public interface JpaGeneroRepository extends JpaRepository<GeneroEntity, Long> {}
