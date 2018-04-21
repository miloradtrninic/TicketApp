package com.msmisa.TicketApp.dto.preview;

import com.msmisa.TicketApp.beans.User;

public class UserAuditoriumPreviewDTO {

	private String pkUserUsername;
	private String pkAuditoriumName;
	private Double rating;
	
	public String getPkUserUsername() {
		return pkUserUsername;
	}
	public void setPkUserUsername(String pkUserUsername) {
		this.pkUserUsername = pkUserUsername;
	}
	public String getPkAuditoriumName() {
		return pkAuditoriumName;
	}
	public void setPkAuditoriumName(String pkAuditoriumName) {
		this.pkAuditoriumName = pkAuditoriumName;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	
	
	
}
