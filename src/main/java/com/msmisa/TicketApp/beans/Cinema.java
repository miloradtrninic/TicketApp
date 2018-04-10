package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@SecondaryTable(name="CINEMAS")
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
