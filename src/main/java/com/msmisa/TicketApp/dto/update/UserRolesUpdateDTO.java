package com.msmisa.TicketApp.dto.update;

import java.util.List;

public class UserRolesUpdateDTO {
	private Integer userID;
	private List<Integer> roles;
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public List<Integer> getRoles() {
		return roles;
	}
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	

	
}
