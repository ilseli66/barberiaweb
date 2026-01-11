package com.skimobarber.gateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;

@Configuration
public class GatewayConfig {

    // ========================================================================
    // 1. IDENTITY SERVICE (Auth, Usuarios, Clientes)
    // ========================================================================
    @Bean
    public RouterFunction<ServerResponse> identityServiceRoute() {
        return GatewayRouterFunctions.route("identity-service")
            .route(RequestPredicates.path("/api/auth/**")
                .or(RequestPredicates.path("/api/usuarios/**"))
                .or(RequestPredicates.path("/api/clientes/**")),
                HandlerFunctions.http())
            .filter(lb("identity-service"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> identityDocRoute() {
        return GatewayRouterFunctions.route("identity-doc-route")
            .route(RequestPredicates.path("/api/identity/v3/api-docs"),
                HandlerFunctions.http())
            .before(stripPrefix(2)) // Quita "/api/identity" -> deja "/v3/api-docs"
            .filter(lb("identity-service"))
            .build();
    }

    // ========================================================================
    // 2. ORGANIZATION SERVICE (Establecimientos, Sucursales, Empleados)
    // ========================================================================
    @Bean
    public RouterFunction<ServerResponse> organizationServiceRoute() {
        return GatewayRouterFunctions.route("organization-service")
            .route(RequestPredicates.path("/api/establecimientos/**")
                .or(RequestPredicates.path("/api/sucursales/**"))
                .or(RequestPredicates.path("/api/empleados/**")),
                HandlerFunctions.http())
            .filter(lb("organization-service"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> organizationDocRoute() {
        return GatewayRouterFunctions.route("organization-doc-route")
            .route(RequestPredicates.path("/api/organization/v3/api-docs"),
                HandlerFunctions.http())
            .before(stripPrefix(2)) // Quita "/api/organization"
            .filter(lb("organization-service"))
            .build();
    }

    // ========================================================================
    // 3. CATALOG SERVICE (Servicios, Listas de precios)
    // ========================================================================
    @Bean
    public RouterFunction<ServerResponse> catalogServiceRoute() {
        return GatewayRouterFunctions.route("catalog-service")
            .route(RequestPredicates.path("/api/servicios/**")
                .or(RequestPredicates.path("/api/listas-precio/**")),
                HandlerFunctions.http())
            .filter(lb("catalog-service"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> catalogDocRoute() {
        return GatewayRouterFunctions.route("catalog-doc-route")
            .route(RequestPredicates.path("/api/catalog/v3/api-docs"),
                HandlerFunctions.http())
            .before(stripPrefix(2)) // Quita "/api/catalog"
            .filter(lb("catalog-service"))
            .build();
    }

    // ========================================================================
    // 4. BOOKING SERVICE (Citas, Disponibilidad, Horarios)
    // ========================================================================
    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute() {
        return GatewayRouterFunctions.route("booking-service")
            .route(RequestPredicates.path("/api/citas/**")
                .or(RequestPredicates.path("/api/disponibilidad/**"))
                .or(RequestPredicates.path("/api/horarios/**")),
                HandlerFunctions.http())
            .filter(lb("booking-service"))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingDocRoute() {
        return GatewayRouterFunctions.route("booking-doc-route")
            .route(RequestPredicates.path("/api/booking/v3/api-docs"),
                HandlerFunctions.http())
            .before(stripPrefix(2)) // Quita "/api/booking"
            .filter(lb("booking-service"))
            .build();
    }
}