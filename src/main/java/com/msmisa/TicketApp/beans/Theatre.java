package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;


@Entity
@SecondaryTable(name="THEATRES", catalog="isadb")
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
