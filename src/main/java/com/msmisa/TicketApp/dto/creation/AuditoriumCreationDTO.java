package com.msmisa.TicketApp.dto.creation;

public class AuditoriumCreationDTO {
	
	private String name;
	private String address;
	private String description;
	//private String type;
	private Integer ratings;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
	/*public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}*/
}
