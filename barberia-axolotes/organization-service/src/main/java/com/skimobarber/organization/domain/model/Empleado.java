package com.skimobarber.organization.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    private Long personaId;
    private Long sucursalId;
    private String especialidad;
}
