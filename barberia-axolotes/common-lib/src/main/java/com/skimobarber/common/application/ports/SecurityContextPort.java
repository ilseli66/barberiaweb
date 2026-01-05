package com.skimobarber.common.application.ports;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de seguridad para abstraer el acceso al contexto de autenticación.
 * Permite que la capa de aplicación obtenga información del usuario sin
 * depender de implementaciones específicas (JWT, Spring Security, etc.).
 */
public interface SecurityContextPort {
    
    /**
     * Obtiene el ID del usuario actualmente autenticado.
     * @return Optional con el userId si está autenticado, empty si no.
     */
    Optional<Long> getCurrentUserId();
    
    /**
     * Obtiene el login/username del usuario actualmente autenticado.
     * @return Optional con el login si está autenticado, empty si no.
     */
    Optional<String> getCurrentUserLogin();
    
    /**
     * Obtiene la lista de roles del usuario actualmente autenticado.
     * @return Lista de roles (puede estar vacía si no está autenticado).
     */
    List<String> getCurrentUserRoles();
    
    /**
     * Verifica si hay un usuario autenticado.
     * @return true si está autenticado, false si no.
     */
    boolean isAuthenticated();
    
    /**
     * Verifica si el usuario actual tiene un rol específico.
     * @param role El rol a verificar.
     * @return true si tiene el rol, false si no.
     */
    default boolean hasRole(String role) {
        return getCurrentUserRoles().contains(role);
    }
}
