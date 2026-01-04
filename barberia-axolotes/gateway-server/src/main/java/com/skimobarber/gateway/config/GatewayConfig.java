package com.skimobarber.gateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> identityServiceRoute() {
        return GatewayRouterFunctions.route("identity-service")
            .route(RequestPredicates.path("/api/usuarios/**")
                .or(RequestPredicates.path("/api/clientes/**")),
                HandlerFunctions.http())
            .filter(lb("identity-service"))
            .build();
    }

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
    public RouterFunction<ServerResponse> catalogServiceRoute() {
        return GatewayRouterFunctions.route("catalog-service")
            .route(RequestPredicates.path("/api/servicios/**")
                .or(RequestPredicates.path("/api/listas-precio/**")),
                HandlerFunctions.http())
            .filter(lb("catalog-service"))
            .build();
    }

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
}
