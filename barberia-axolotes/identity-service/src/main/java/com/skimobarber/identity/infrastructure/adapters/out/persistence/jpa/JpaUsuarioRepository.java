package com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.UsuarioEntity;

import java.util.Optional;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByLogin(String login);
    boolean existsByLogin(String login);
}
