package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;


@Entity
@DiscriminatorValue("theatre")
public class Theatre extends Auditorium {
	private Set<Play> plays;

	@ManyToMany(mappedBy="theatre")
	public Set<Play> getPlays() {
		return plays;
	}

	public void setPlays(Set<Play> plays) {
		this.plays = plays;
	}
	
	
}
