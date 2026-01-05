package com.skimobarber.organization.infrastructure.adapters.out.persistence.base;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skimobarber.common.domain.ports.out.IBaseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class JpaBaseAdapter<D, E, ID, R extends JpaRepository<E, ID>>
    implements  IBaseRepository<D, ID> {
        
    protected final R repository;
    protected final Function<E, D> toDomain;
    protected final Function<D, E> toEntity;

    @Override
    public D save(D domain) {
        E entity = toEntity.apply(domain);
        return toDomain.apply(repository.save(entity));
    }

    @Override
    public Optional<D> findById(ID id) {
        return repository.findById(id).map(toDomain);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<D> findAll() {
        return repository.findAll().stream().map(toDomain).toList();
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }   
}
