package br.com.ebac.memelandia.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class GatewayConfig {

    @Value("${USUARIO_SERVICE_URL:http://localhost:9001}")
    private String usuarioServiceUrl;

    @Value("${CATEGORIA_SERVICE_URL:http://localhost:9002}")
    private String categoriaServiceUrl;

    @Value("${MEMES_SERVICE_URL:http://localhost:9003}")
    private String memesServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> usuarioServiceRoute() {
        return route("usuario-service").route(RequestPredicates.path("/api/usuarios/**"), HandlerFunctions.http(usuarioServiceUrl)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> categoriaServiceRoute() {
        return route("categoria-service").route(RequestPredicates.path("/api/categorias/**"), HandlerFunctions.http(categoriaServiceUrl)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> memesServiceRoute() {
        return route("memes-service").route(RequestPredicates.path("/api/memes/**"), HandlerFunctions.http(memesServiceUrl)).build();
    }
}
