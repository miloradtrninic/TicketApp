package com.msmisa.TicketApp.beans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserAuditoriumId implements Serializable {
	@ManyToOne
	@JoinColumn(nullable=false, name="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="AUDITORIUM_ID")
	private Auditorium auditorium;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Auditorium getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	
}
