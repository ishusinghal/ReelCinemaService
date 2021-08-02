package com.reel.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reel.api.data.Film;
import com.reel.api.data.repo.FilmRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@WebFluxTest
public abstract class AbstractBaseFilmEndpoints {

    private final WebTestClient client; // <2>

    @MockBean  // <3>
    private FilmRepo repository;

    public AbstractBaseFilmEndpoints(WebTestClient client) {
        this.client = client;
    }

    @Test
    public void getAll() {

        log.info("running  " + this.getClass().getName());

        // <4>
        Mockito
            .when(this.repository.findAll())
            .thenReturn(Flux.just(new Film(UUID.randomUUID().toString(), 
					"sholay", "A film ",
					LocalDateTime.now().minusYears(20L), 5, 25.20F, "INDIA",
					List.of("THRILLER", "ACTION", "COMEDY"), "sholay", List.of()), new Film(UUID.randomUUID().toString(), 
							"godfather", "A film ",
							LocalDateTime.now().minusYears(20L), 5, 25.20F, "USA",
							List.of("THRILLER", "ACTION", "COMEDY"), "godfather", List.of())));

        // <5>
        this.client
            .get()
            .uri("/films")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[0].name").isEqualTo("sholay")
            .jsonPath("$.[0].slugLine").isEqualTo("sholay");
    }

    @Test
    public void save() {
        Film data = new Film(UUID.randomUUID().toString(), 
				"sholay", "A film ",
				LocalDateTime.now().minusYears(20L), 5, 25.20F, "INDIA",
				List.of("THRILLER", "ACTION", "COMEDY"), "sholay", List.of());
        Mockito
            .when(this.repository.save(Mockito.any(Film.class)))
            .thenReturn(Mono.just(data));
        MediaType jsonUtf8 = MediaType.APPLICATION_JSON;
        this
            .client
            .post()
            .uri("/films")
            .contentType(jsonUtf8)
            .body(Mono.just(data), Film.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(jsonUtf8);
    }

     @Test
     public void getBySlug() {

    	   Film data = new Film(UUID.randomUUID().toString(), 
   				"sholay", "A film ",
   				LocalDateTime.now().minusYears(20L), 5, 25.20F, "INDIA",
   				List.of("THRILLER", "ACTION", "COMEDY"), "sholay", List.of());

        Mockito
            .when(this.repository.findBySlugLine(data.getSlugLine()))
            .thenReturn(Flux.just(data));

        this.client
            .get()
            .uri("/films/" + data.getSlugLine())
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.id").isEqualTo(data.getId())
            .jsonPath("$.name").isEqualTo(data.getName());
    }
}