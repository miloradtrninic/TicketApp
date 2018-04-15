package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@DiscriminatorValue("cinema")
public class Cinema extends Auditorium {
	private Set<Movie> moviesRepertory;
	
	@ManyToMany(mappedBy="cinemaList")
	@JsonIgnore
	public Set<Movie> getMoviesRepertory() {
		return moviesRepertory;
	}

	public void setMoviesRepertory(Set<Movie> moviesRepertory) {
		this.moviesRepertory = moviesRepertory;
	}
	
	
}
