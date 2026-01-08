package com.skimobarber.identity.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private Long id;
    private Long generoId;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;

    // Domain validations
    public boolean isEmailValid() {
        return email != null && email.contains("@") && email.length() >= 5;
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder(nombre);
        sb.append(" ").append(primerApellido);
        if (segundoApellido != null && !segundoApellido.isBlank()) {
            sb.append(" ").append(segundoApellido);
        }
        return sb.toString();
    }
}
