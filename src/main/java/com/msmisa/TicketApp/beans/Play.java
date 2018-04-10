package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(name="PLAYS")
public class Play extends Projection {
	private List<Theatre> theatre;

	@ManyToMany
	@JoinTable(name="THEATRE_PLAYS", joinColumns=@JoinColumn(name="PLAY_ID"), inverseJoinColumns=@JoinColumn(name="THEATRE_ID"))
	public List<Theatre> getTheatre() {
		return theatre;
	}

	public void setTheatre(List<Theatre> theatre) {
		this.theatre = theatre;
	}
	
	
}
