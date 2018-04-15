package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@DiscriminatorValue("cinema")
public class Cinema extends Auditorium {
	private List<Movie> moviesRepertory;
	
	@ManyToMany(mappedBy="cinemaList")
	@JsonIgnore
	public List<Movie> getMoviesRepertory() {
		return moviesRepertory;
	}

	public void setMoviesRepertory(List<Movie> moviesRepertory) {
		this.moviesRepertory = moviesRepertory;
	}
	
	
}
