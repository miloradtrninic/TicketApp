package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("movie")
public class Movie extends Projection {
	private List<Cinema> cinemaList;

	@ManyToMany
	@JoinTable(name="CINEMA_MOVIE", joinColumns=@JoinColumn(name="MOVIE_ID", nullable=false), inverseJoinColumns=@JoinColumn(name="CINEMA_ID", nullable=false))
	@JsonIgnore
	public List<Cinema> getCinemaList() {
		return cinemaList;
	}

	public void setCinemaList(List<Cinema> cinemaList) {
		this.cinemaList = cinemaList;
	}
	
	
}
