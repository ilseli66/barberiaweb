package com.skimobarber.organization.domain.model;

import java.util.List;

import com.skimobarber.common.domain.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
    private Long id;
    private Long establecimientoId;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private List<Empleado> empleados;

    // Domain validation
    public boolean isNombreValid() {
        return nombre != null && !nombre.isBlank() && nombre.length() <= 100;
    }

    public boolean hasUbicacion() {
        return latitud != null && longitud != null;
    }

    public Result<Void> assignEmployee(Empleado empleado) {
        if (empleado.getSucursalId() != null && empleado.getSucursalId().equals(this.id)) {
            return Result.conflict("El empleado ya pertenece a la sucursal.");
        } 

        this.empleados.add(empleado);
        
        return Result.accepted(null, "Empleado asignado a la sucursal correctamente.");
    }
}
