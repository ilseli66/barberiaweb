package com.skimobarber.identity.domain.model;

import com.skimobarber.identity.domain.enums.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long personaId;
    private String login;
    private String password;
    private TipoRol rol;
    private boolean activo;

    // Domain validations
    public boolean isLoginValid() {
        return login != null && login.length() >= 5;
    }

    public boolean isPasswordSecure() {
        return password != null && password.length() >= 8;
    }
}
