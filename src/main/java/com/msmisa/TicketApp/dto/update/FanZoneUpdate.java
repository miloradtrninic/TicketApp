package com.msmisa.TicketApp.dto.update;

import java.util.List;

public class FanZoneUpdate {
	public Integer id;
	public List<Integer> adminFKs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Integer> getAdminFKs() {
		return adminFKs;
	}
	public void setAdminFKs(List<Integer> adminFKs) {
		this.adminFKs = adminFKs;
	}
	
	
}
