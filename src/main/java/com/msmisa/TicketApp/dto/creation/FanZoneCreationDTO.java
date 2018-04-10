package com.msmisa.TicketApp.dto.creation;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class FanZoneCreationDTO {
	@ForeignKeyDTO(clazzFK=User.class)
	private String adminId;
	
	@ForeignKeyDTO(clazzFK=Auditorium.class)
	private Integer auditoriumId;
	
	public String getAdminUsername() {
		return adminId;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminId = adminUsername;
	}
	public Integer getAuditoriumId() {
		return auditoriumId;
	}
	public void setAuditoriumId(Integer auditoriumId) {
		this.auditoriumId = auditoriumId;
	}
	
	
}
