package com.msmisa.TicketApp.beans;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue("movie")
public class Movie extends Projection {
	
	private Cinema cinema;
	
	@ManyToOne
	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	
	
	
	
}
