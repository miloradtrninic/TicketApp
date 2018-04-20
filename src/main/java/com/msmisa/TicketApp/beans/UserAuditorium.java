package com.msmisa.TicketApp.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="USER_AUDITORIUM")
@Entity
public class UserAuditorium {
	
	@EmbeddedId
	private UserAuditoriumId pk;
	
	private Double rating;
	
	public Double getRating() {
		return rating;
	}
	public UserAuditoriumId getPk() {
		return pk;
	}
	public void setPk(UserAuditoriumId pk) {
		this.pk = pk;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public User getUser() {
		return this.pk == null ? null : this.pk.getUser();
	}
	public void setUser(User user) {
		if(this.pk == null){
			this.pk = new UserAuditoriumId();
		}
		this.pk.setUser(user);
	}
	public Auditorium getAuditorium() {
		return this.pk == null ? null : this.pk.getAuditorium();
	}
	public void setAuditorium(Auditorium auditorium) {
		if(this.pk == null){
			this.pk = new UserAuditoriumId();
		}
		this.pk.setAuditorium((auditorium));
	}
}
