package com.reel.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.reel.api.data.Film;
import com.reel.api.data.repo.FilmRepo;
import com.reel.events.FilmEvent;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class FilmService {
	
	private final ApplicationEventPublisher eventPublisher;
	private final FilmRepo repo;
	
	public FilmService(ApplicationEventPublisher eventPublisher, FilmRepo repo) {
		super();
		this.eventPublisher = eventPublisher;
		this.repo = repo;
	}
	
	/**
	 * 
	 * Get all the films
	 * @return
	 */
	public Flux<Film> all() {
		log.info("Getting all");
		return this.repo.findAll();
	}
	
	/**Get films based on the slugLines
	 * 
	 * @param slugLine
	 * @return
	 */
	public Flux<Film> get(String slugLine) {
		log.info("Getting film with slugLine {}", slugLine);
		return this.repo.findBySlugLine(slugLine).doOnNext(onNxt -> this.eventPublisher.publishEvent(new FilmEvent(onNxt)));
	}
	
	/**
	 * 
	 * Create new film
	 * @param film
	 * @return
	 */
	public Mono<Film> create(Film film) {
		log.info("Creating the film: {}", film);
		return this.repo.save(film)
				.doOnSuccess(movie -> this.eventPublisher.publishEvent(new FilmEvent(movie)));
	}

}
