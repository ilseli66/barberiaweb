package com.skimobarber.organization.infrastructure.adapters.out.persistence.mappers;

import java.util.List;

import com.skimobarber.organization.domain.model.Empleado;
import com.skimobarber.organization.domain.model.Sucursal;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.EmpleadoEntity;
import com.skimobarber.organization.infrastructure.adapters.out.persistence.entity.SucursalEntity;

public class SucursalMapper {
    public static Sucursal toDomain(SucursalEntity entity) {
        if (entity == null) return null;
        List<Empleado> empleados = entity.getEmpleados().stream()
                .map(EmpleadoMapper::toDomain)
                .toList();
        return new Sucursal(entity.getId(), entity.getEstablecimientoId(), 
                            entity.getNombre(), entity.getLatitud(), 
                            entity.getLongitud(), empleados);
    }

    public static SucursalEntity toEntity(Sucursal domain) {
        if (domain == null) return null;
        SucursalEntity entity = new SucursalEntity();
        entity.setId(domain.getId());
        entity.setEstablecimientoId(domain.getEstablecimientoId());
        entity.setNombre(domain.getNombre());
        entity.setLatitud(domain.getLatitud());
        entity.setLongitud(domain.getLongitud());
        
        if (domain.getEmpleados() != null) {
            domain.getEmpleados().forEach(emp -> {
                EmpleadoEntity empEntity = EmpleadoMapper.toEntity(emp);
                entity.addEmpleado(empEntity);
            });
        }
        return entity;
    }
}
