package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

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
	private Set<Cinema> cinemaList;

	@ManyToMany
	@JoinTable(name="CINEMA_MOVIE", joinColumns=@JoinColumn(name="MOVIE_ID", nullable=false), inverseJoinColumns=@JoinColumn(name="CINEMA_ID", nullable=false))
	@JsonIgnore
	public Set<Cinema> getCinemaList() {
		return cinemaList;
	}

	public void setCinemaList(Set<Cinema> cinemaList) {
		this.cinemaList = cinemaList;
	}
	
	
}
