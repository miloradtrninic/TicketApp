package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;


@Entity
@SecondaryTable(name="THEATRES")
public class Theatre extends Auditorium {
	private List<Play> plays;

	@ManyToMany(mappedBy="theatre")
	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}
	
	
}
