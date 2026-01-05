package com.skimobarber.catalog.infrastructure.adapters.out.persistence;

import com.skimobarber.catalog.domain.model.ListaPrecio;
import com.skimobarber.catalog.domain.model.ServicioListaPrecio;
import com.skimobarber.catalog.domain.ports.out.IListaPrecioRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.base.JpaBaseAdapter;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ListaPrecioEntity;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.entity.ServicioListaPrecioEntity;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa.JpaListaPrecioRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.jpa.JpaServicioListaPrecioRepository;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper.ListaPrecioMapper;
import com.skimobarber.catalog.infrastructure.adapters.out.persistence.mapper.ServicioListaPrecioMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class ListaPrecioPersistenceAdapter extends JpaBaseAdapter<ListaPrecio, ListaPrecioEntity, Long, JpaListaPrecioRepository> implements IListaPrecioRepository {
    private final JpaServicioListaPrecioRepository slpRepository;

    public ListaPrecioPersistenceAdapter(JpaListaPrecioRepository repository, JpaServicioListaPrecioRepository slpRepository) {
        super(repository, ListaPrecioMapper::toDomain, ListaPrecioMapper::toEntity);
        this.slpRepository = slpRepository;
    }

    @Override
    public List<ListaPrecio> findVigentes() {
        return repository.findVigentes().stream().map(toDomain).toList();
    }

    @Override
    public void saveServicioListaPrecio(ServicioListaPrecio slp) {
        ServicioListaPrecioEntity entity = ServicioListaPrecioMapper.toEntity(slp);
        slpRepository.save(entity);
    }

    @Override
    public Optional<BigDecimal> findPrecio(Long servicioId, Long listaPrecioId) {
        return slpRepository.findPrecio(servicioId, listaPrecioId);
    }

    @Override
    public Optional<ServicioListaPrecio> findPrecioVigente(Long servicioId) {
        return slpRepository.findPrecioVigente(servicioId).map(ServicioListaPrecioMapper::toDomain);
    }
}
