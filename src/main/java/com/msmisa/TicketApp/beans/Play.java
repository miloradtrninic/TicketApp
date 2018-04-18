package com.msmisa.TicketApp.beans;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(name="PLAYS")
public class Play extends Projection {
	
	private Theatre theatre;

	@ManyToOne
	@JoinColumn(name="THEATRE_ID")
	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	
	
}
