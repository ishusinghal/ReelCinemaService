package com.reel.api.handler;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reel.api.data.Film;
import com.reel.service.FilmService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@CrossOrigin("*")
public class FilmHandler {
	
    private final FilmService filmService;

    public FilmHandler(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * GET based on slugName
     * @param r
     * @return
     */
    public Mono<ServerResponse> getBySlugName(ServerRequest r) {
        return defaultReadResponse(this.filmService.get(slugName(r)));
    }

    /**
     * 
     * GET ALL
     * @param r
     * @return
     */
    public Mono<ServerResponse> all(ServerRequest r) {
        return defaultReadResponse(this.filmService.all());
    }

    /**
     * Create new film
     * 
     * @param request
     * @return
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Film> flux = request
            .bodyToFlux(Film.class)
            .flatMap(film -> this.filmService.create(film));
        return defaultWriteResponse(flux);
    }

    /**
     * Write the response in db and emit an event to be posted on the Web socket
     * 
     * @param films
     * @return 
     */
    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Film> films) {
        return Mono
            .from(films)
            .flatMap(f -> ServerResponse
                .created(URI.create("/films/" + f.getSlugLine()))
                .contentType(MediaType.APPLICATION_JSON)
                .build()
            );
    }

    // <4>
    private static Mono<ServerResponse> defaultReadResponse(Publisher<Film> film) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(film, Film.class);
    }

    private static String slugName(ServerRequest r) {
        return r.pathVariable("slugName");
    }

}
