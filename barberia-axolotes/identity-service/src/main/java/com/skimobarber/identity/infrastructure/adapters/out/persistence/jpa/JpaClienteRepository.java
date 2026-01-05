package com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.ClienteEntity;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
