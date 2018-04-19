package com.msmisa.TicketApp.beans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class WatchedProjectionId implements Serializable{
	@ManyToOne
	@JoinColumn(nullable=false, name="USER_ID")
	private User user;
	@ManyToOne
	@JoinColumn(nullable=false, name="PROJECTION_ID")
	private Projection projection;
	

	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	
}
