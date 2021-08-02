package com.reel.events;

import org.springframework.context.ApplicationEvent;

import com.reel.api.data.Film;

public class FilmEvent extends ApplicationEvent {


	private static final long serialVersionUID = 920736942431760100L;

	public FilmEvent(Film source) {
		super(source);
		
	}

	
}
