package com.reel.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import com.reel.api.data.Film;
import com.reel.api.data.repo.FilmRepo;
import com.reel.service.FilmService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@DataMongoTest // <1>
@Import(FilmService.class) // <2>
public class FilmServiceTest {

    private final FilmService service;
    private final FilmRepo repository;

    public FilmServiceTest(@Autowired FilmService service, // <3>
                              @Autowired FilmRepo repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test // <4>
    public void getAll() {
        Flux<Film> saved = repository.saveAll(Flux.just(new Film(UUID.randomUUID().toString(), 
				"sholay",  ": A film ",
				LocalDateTime.now().minusYears(20L), 5, 25.20F, "INDIA",
				List.of("THRILLER", "ACTION", "COMEDY"), "sholay", List.of()),
        		new Film(UUID.randomUUID().toString(), 
        				"godfather",  ": A film ",
        				LocalDateTime.now().minusYears(20L), 5, 25.20F, "USA",
        				List.of("THRILLER", "ACTION", "COMEDY"), "godfather", List.of()))
				);

        Flux<Film> composite = service.all().thenMany(saved);

        Predicate<Film> match = film -> saved.any(saveItem -> saveItem.equals(film)).block();

        StepVerifier
            .create(composite) // <5>
            .expectNextMatches(match)  // <6>
            .expectNextMatches(match)  // <6>
            .verifyComplete(); // <7>
    }

    @Test
    public void save() {
    	Film film = new Film(UUID.randomUUID().toString(), 
				"test", ": A film ",
				LocalDateTime.now().minusYears(20L), 5, 25.20F, "INDIA",
				List.of("THRILLER", "ACTION", "COMEDY"), "test", List.of());
        Mono<Film> filmMono = this.service.create(film);
        StepVerifier
            .create(filmMono)
            .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
            .verifyComplete();
    }

}