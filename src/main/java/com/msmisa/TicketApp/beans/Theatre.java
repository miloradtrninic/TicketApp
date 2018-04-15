package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;


@Entity
@DiscriminatorValue("theatre")
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
