package com.msmisa.TicketApp.dto.preview;

import java.util.List;

public class FanZonePreviewDTO {
	private Integer id;
	private String adminUsername;
	private String auditoriumName;
	private List<FanItemPreviewDTO> fanitemList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAuditoriumName() {
		return auditoriumName;
	}
	public void setAuditoriumName(String auditoriumName) {
		this.auditoriumName = auditoriumName;
	}
	public List<FanItemPreviewDTO> getFanitemList() {
		return fanitemList;
	}
	public void setFanitemList(List<FanItemPreviewDTO> fanitemList) {
		this.fanitemList = fanitemList;
	}
	
	
}
