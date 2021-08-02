package com.reel.api.data.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reel.api.data.Film;

import reactor.core.publisher.Flux;

@Repository
public interface FilmRepo extends ReactiveMongoRepository<Film, String>{
	
	public Flux<Film> findBySlugLine(String slugLine);
}
