package com.reel.api.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reel.api.handler.FilmHandler;;

@Configuration
public class ApiConfiguration {
	
	/**
	 * 
	 * Router function for the routes 
	 * and mapping with the service
	 * 
	 * 
	 * @param handler
	 * @return 
	 */
	@Bean
    RouterFunction<ServerResponse> routes(FilmHandler handler) {
        return route(GET("/films"), handler::all) // 
            .andRoute(GET("/films/{slugName}"), handler::getBySlugName)
            .andRoute(POST("/films"), handler::create)
        .and(resources("/**", new ClassPathResource("static/")));
    }


}
