package com.skimobarber.identity.domain.ports.in;

import java.util.List;

import com.skimobarber.common.domain.Result;
import com.skimobarber.identity.domain.model.Genero;

public interface GeneroUseCase {
    
    Result<Genero> create(CreateGeneroCommand command);
    
    Result<Genero> findById(Long id);
    
    Result<List<Genero>> findAll();
    
    Result<List<Genero>> findAllActivos();
    
    Result<Genero> update(Long id, UpdateGeneroCommand command);
    
    Result<Void> delete(Long id);
    
    Result<Genero> activate(Long id);
    
    Result<Genero> deactivate(Long id);

    record CreateGeneroCommand(String nombre) {}

    record UpdateGeneroCommand(String nombre, boolean activo) {}
}
