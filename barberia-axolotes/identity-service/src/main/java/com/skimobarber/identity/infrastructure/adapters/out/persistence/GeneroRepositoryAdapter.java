package com.skimobarber.identity.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Repository;

import com.skimobarber.identity.domain.model.Genero;
import com.skimobarber.identity.domain.ports.out.IGeneroRepository;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.entity.GeneroEntity;
import com.skimobarber.identity.infrastructure.adapters.out.persistence.jpa.JpaGeneroRepository;

@Repository
public class GeneroRepositoryAdapter extends JpaBaseAdapter<Genero, GeneroEntity, Long, JpaGeneroRepository> implements IGeneroRepository {
    public GeneroRepositoryAdapter(JpaGeneroRepository jpaRepository) {
        super(jpaRepository, GeneroEntity::toDomain, GeneroEntity::fromDomain);
    }
}
