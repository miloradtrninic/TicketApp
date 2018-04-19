package com.msmisa.TicketApp.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;

@Table(name="WATCHED_PROJECTION")
@Entity
public class WatchedProjection {
	
	@EmbeddedId
	private WatchedProjectionId pk;
	
	private Double rating;
	
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public WatchedProjectionId getPk() {
		return pk;
	}
	public void setPk(WatchedProjectionId pk) {
		this.pk = pk;
	}
	public User getUser() {
		return this.pk.getUser();
	}
	public void setUser(User user) {
		this.pk.setUser(user);
	}
	public Projection getProjection() {
		return this.pk.getProjection();
	}
	public void setProjection(Projection projection) {
		this.pk.setProjection(projection);
	}
	
	
	
}
